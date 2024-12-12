package gcu.smapill_back.converter;

import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.web.dto.PrescriptionRequestDTO;
import gcu.smapill_back.web.dto.PrescriptionResponseDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class PrescriptionConverter {
    public static Prescription toPrescriptionResultDTO(PrescriptionRequestDTO.CreatePrescriptionDTO request) {
        return Prescription.builder()
                .name(request.getName())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .duration(request.getDuration())
                .instruction(request.getInstruction())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public static PrescriptionResponseDTO.CreatePrescriptionResultDTO toCreateResultDTO(Prescription prescription) {
        return PrescriptionResponseDTO.CreatePrescriptionResultDTO.builder()
                .prescriptionId(prescription.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}