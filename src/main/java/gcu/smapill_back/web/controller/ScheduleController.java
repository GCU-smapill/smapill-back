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
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public ApiResponse<List<ScheduleResponseDTO.CreateScheduleResultDTO>> createSchedule(@Valid @AuthenticationPrincipal UserDetail userDetail, @RequestBody List<ScheduleRequestDTO.CreateScheduleDTO> requestList, @RequestHeader("prescription_id") Long prescriptionId) throws Exception {
        Prescription prescription = prescriptionService.checkPrescription(prescriptionId);

        List<ScheduleResponseDTO.CreateScheduleResultDTO> resultList = new ArrayList<>();

        for(ScheduleRequestDTO.CreateScheduleDTO request : requestList) {
            Schedule schedule = scheduleService.createCustomSchedule(request, prescription, userDetail.getUser());
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

    @GetMapping("/")
    @Operation(summary = "복용 일정 조회 API", description = "복용 일정 조회 API(날짜 기반)")
    public ApiResponse<Map<String, Map<String, ScheduleResponseDTO.GetScheduleResultListDTO>>> getSchedule(@RequestParam @NotNull(message = " 값은 필수 입력 값입니다.") LocalDate scheduleDate,
                                                                                 @AuthenticationPrincipal UserDetail userDetail) {
        Map<String, Map<String, ScheduleResponseDTO.GetScheduleResultListDTO>> scheduleList = scheduleService.getScheduleList(userDetail.getUser().getId(), scheduleDate);
        return ApiResponse.onSuccess(scheduleList);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "복용 일정 삭제 API", description = "복용 일정 삭제 API")
    public ApiResponse<?> deleteSchedule(@PathVariable Long scheduleId,
                                       @AuthenticationPrincipal UserDetail userDetail){
        scheduleService.deleteSchedule(userDetail.getUser().getId(), scheduleId);
        return ApiResponse.onSuccess(null);
    }
}
