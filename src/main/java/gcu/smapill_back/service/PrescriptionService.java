package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.PrescriptionHandler;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.converter.PrescriptionConverter;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.repository.PrescriptionRepository;
import gcu.smapill_back.repository.ScheduleRepository;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.web.dto.PrescriptionRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    public User checkUser(Long userId){
        //userId로 유저가 존재하는 지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));
        if(user == null){
            throw new UserHandler(ErrorStatus.NO_USER_EXIST);
        }

        return user;
    }

    public Prescription checkPrescription(Long Prescriptionid){
        Prescription prescription = prescriptionRepository.findById(Prescriptionid)
                .orElseThrow(() -> new PrescriptionHandler(ErrorStatus.NO_PRESCRIPTION_EXIST));
        if(prescription == null){
            throw new PrescriptionHandler(ErrorStatus.NO_PRESCRIPTION_EXIST);
        }

        return prescription;
    }
    @Transactional
    public Prescription createPrescription(Long userId, PrescriptionRequestDTO.CreatePrescriptionDTO request) {
        User user = checkUser(userId);
        Prescription prescription = PrescriptionConverter.toPrescriptionResultDTO(request);

        prescription.setUser(user);

        return prescriptionRepository.save(prescription);
    }

    @Transactional
    public void connectToSchedule(Long userId, Long prescriptionId) {
        List<Schedule> schedules = scheduleService.createSchedules(userId, prescriptionId);
        scheduleRepository.saveAll(schedules);
    }
}
