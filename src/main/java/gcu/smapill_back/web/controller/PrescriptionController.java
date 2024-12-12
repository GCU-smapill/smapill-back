package gcu.smapill_back.web.controller;

import gcu.smapill_back.apiPayload.exception.ApiResponse;
import gcu.smapill_back.converter.PrescriptionConverter;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.service.PrescriptionService;
import gcu.smapill_back.service.UserService;
import gcu.smapill_back.web.dto.PrescriptionRequestDTO;
import gcu.smapill_back.web.dto.PrescriptionResponseDTO;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/prescription")
@Tag(name = "prescription", description = "처방전 API")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @PostMapping("/")
    @Operation(summary = "처방전 생성 API", description = "처방전 생성 API")
    public ApiResponse<PrescriptionResponseDTO.CreatePrescriptionResultDTO> createPrescription(@Valid @RequestBody PrescriptionRequestDTO.CreatePrescriptionDTO request, @RequestHeader("user_id") Long userId) throws Exception {
        Prescription prescription = prescriptionService.createPrescription(userId, request);
        return ApiResponse.onSuccess(PrescriptionConverter.toCreateResultDTO(prescription));
    }
}
