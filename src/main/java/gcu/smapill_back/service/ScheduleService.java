package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.PrescriptionHandler;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.converter.ScheduleConverter;
import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.enums.TimeOfDay;
import gcu.smapill_back.repository.PrescriptionRepository;
import gcu.smapill_back.repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;

    public List<Schedule> createSchedules(Long userId, Long prescriptionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new PrescriptionHandler(ErrorStatus.NO_PRESCRIPTION_EXIST));

        //약 명칭
        String name = prescription.getName();

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
                Schedule schedule = ScheduleConverter.toSchedule(name, currentDate, timeOfDay, prescription);
                schedules.add(schedule);
            }
        }

        return schedules;
    }

    //약 명칭
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_of_day")
    private TimeOfDay timeOfDay;
}
