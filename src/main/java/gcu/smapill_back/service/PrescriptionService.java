package gcu.smapill_back.service;

import gcu.smapill_back.apiPayload.code.status.ErrorStatus;
import gcu.smapill_back.apiPayload.exception.handler.UserHandler;
import gcu.smapill_back.converter.PrescriptionConverter;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.repository.PrescriptionRepository;
import gcu.smapill_back.repository.UserRepository;
import gcu.smapill_back.web.dto.PrescriptionRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final UserRepository userRepository;
    private final PrescriptionRepository prescriptionRepository;

    public User checkUser(Long userId){
        //userId로 유저가 존재하는 지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.NO_USER_EXIST));
        if(user == null){
            throw new UserHandler(ErrorStatus.NO_USER_EXIST);
        }

        return user;
    }

    public Prescription createPrescription(Long userId, PrescriptionRequestDTO.CreatePrescriptionDTO request) {
        User user = checkUser(userId);
        Prescription prescription = PrescriptionConverter.toPrescriptionResultDTO(request);

        prescription.setUser(user);

        return prescriptionRepository.save(prescription);
    }
}
