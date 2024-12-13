package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.PrescriptionHandler;
import gcu.smapill_back.apiPayload.exception.handler.ScheduleHandler;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.converter.PrescriptionConverter;
import gcu.smapill_back.converter.ScheduleConverter;
import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.enums.TimeOfDay;
import gcu.smapill_back.repository.PrescriptionRepository;
import gcu.smapill_back.repository.ScheduleRepository;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.web.dto.ScheduleRequestDTO;
import gcu.smapill_back.web.dto.ScheduleResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> createSchedules(Long userId, Long prescriptionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionHandler(ErrorStatus.NO_PRESCRIPTION_EXIST));

        //시작 날짜
        LocalDate startDate = prescription.getStartDate();

        //복용 기간
        int duration = prescription.getDuration();

        //복용 횟수
        int frequency = prescription.getFrequency();

        List<Schedule> schedules = new ArrayList<>();

        //스케줄 날짜(for문): prescription의 startDate ~ duration동안. (LocalDate 타입)
        for(int i = 0; i < duration; i++) {
            LocalDate currentDate = startDate.plusDays(i);

            //아침, 점심, 저녁 중 언제 먹는 지
            List<TimeOfDay> timeSlots = switch (frequency) {
                case 2 -> List.of(TimeOfDay.MORNING, TimeOfDay.EVENING);
                case 3 -> List.of(TimeOfDay.MORNING, TimeOfDay.AFTERNOON, TimeOfDay.EVENING);
                default -> List.of(TimeOfDay.ANYTIME);
            };

            for (TimeOfDay timeOfDay : timeSlots) {
                Schedule schedule = ScheduleConverter.toSchedule(currentDate, timeOfDay, prescription, user);
                schedules.add(schedule);
            }
        }

        return schedules;
    }

    @Transactional
    public Schedule createCustomSchedule(ScheduleRequestDTO.CreateScheduleDTO request, Prescription prescription, User user) {

        Schedule schedule = ScheduleConverter.toScheduleResultDTO(request);
        schedule.setPrescription(prescription);
        schedule.setUser(user);

        //user.getScheduleList().add(schedule);
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(Long userId, Long scheduleId, ScheduleRequestDTO.UpdateIsTakenDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleHandler(ErrorStatus.NO_SCHEDULE_EXIST));


        schedule.updateSchedule(request.getIsTaken());

        return scheduleRepository.save(schedule);
    }

    public Map<String, Map<String, ScheduleResponseDTO.GetScheduleResultListDTO>> getScheduleList(Long userId, LocalDate scheduleDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));


        TimeOfDay[] timeOfDays = TimeOfDay.values();

        Map<String, Map<String, ScheduleResponseDTO.GetScheduleResultListDTO>> result = new LinkedHashMap<>();
        Map<String, ScheduleResponseDTO.GetScheduleResultListDTO> dateSchedule = new LinkedHashMap<>();

        for(TimeOfDay timeOfDay : timeOfDays) {
            List<Schedule> schedules = scheduleRepository.findAllByUserAndScheduleDateAndTimeOfDay(user, scheduleDate, timeOfDay);

            ScheduleResponseDTO.GetScheduleResultListDTO results = ScheduleConverter.toGetScheduleResultListDTO(schedules, timeOfDay);
            dateSchedule.put(timeOfDay.name(), results);
        }

        result.put(scheduleDate.toString(), dateSchedule);

        return result;
    }
}
