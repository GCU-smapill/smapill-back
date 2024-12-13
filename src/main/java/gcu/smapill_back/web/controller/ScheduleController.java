package gcu.smapill_back.web.controller;

import gcu.smapill_back.apiPayload.exception.ApiResponse;
import gcu.smapill_back.config.auth.UserDetail;
import gcu.smapill_back.converter.PrescriptionConverter;
import gcu.smapill_back.converter.ScheduleConverter;
import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.repository.ScheduleRepository;
import gcu.smapill_back.service.PrescriptionService;
import gcu.smapill_back.service.ScheduleService;
import gcu.smapill_back.web.dto.PrescriptionRequestDTO;
import gcu.smapill_back.web.dto.PrescriptionResponseDTO;
import gcu.smapill_back.web.dto.ScheduleRequestDTO;
import gcu.smapill_back.web.dto.ScheduleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final PrescriptionService prescriptionService;
    private final ScheduleRepository scheduleRepository;

    @PostMapping
    @Operation(summary = "복용 일정 생성 API", description = "복용 일정 생성 API")
    public ApiResponse<List<ScheduleResponseDTO.CreateScheduleResultDTO>> createSchedule(@Valid @RequestBody List<ScheduleRequestDTO.CreateScheduleDTO> requestList, @RequestHeader("prescription_id") Long prescriptionId) throws Exception {
        Prescription prescription = prescriptionService.checkPrescription(prescriptionId);

        List<ScheduleResponseDTO.CreateScheduleResultDTO> resultList = new ArrayList<>();

        for(ScheduleRequestDTO.CreateScheduleDTO request : requestList) {
            Schedule schedule = scheduleService.createCustomSchedule(request, prescription);
            resultList.add(ScheduleConverter.toCreateResultDTO(schedule));
        }

        return ApiResponse.onSuccess(resultList);
    }

    @PatchMapping("/{scheduleId}")
    @Operation(summary = "복용 상태 수정 API", description = "복용 여부를 수정하는 API")
    public ApiResponse<ScheduleResponseDTO.UpdateScheduleResultDTO> updateIsTaken(@AuthenticationPrincipal UserDetail userDetail, @RequestBody @Valid ScheduleRequestDTO.UpdateIsTakenDTO request, @PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.updateSchedule(userDetail.getUser().getId(), scheduleId, request);
        return ApiResponse.onSuccess(ScheduleConverter.toUpdateScheduleResultDTO(schedule));
    }
}