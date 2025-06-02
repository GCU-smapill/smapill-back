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
import io.swagger.v3.oas.annotations.Parameter;
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

    @PostMapping("/users/{userId}")
    @Operation(summary = "복용 일정 생성 API", description = "복용 일정 생성 API")
    public ApiResponse<ScheduleResponseDTO.CreateScheduleResultListDTO> createSchedule(@Parameter(description = "유저 ID", example = "1") @PathVariable Long userId, @Valid @RequestBody ScheduleRequestDTO.CreateScheduleDTO request) throws Exception {
        List<Schedule> schedules = scheduleService.createCustomSchedule(request, userId);

        return ApiResponse.onSuccess(ScheduleConverter.toCreateResultListDTO(schedules));
    }

    @PatchMapping("/{scheduleId}/users/{userId}")
    @Operation(summary = "복용 상태 수정 API", description = "복용 여부를 수정하는 API")
    public ApiResponse<ScheduleResponseDTO.UpdateScheduleResultDTO> updateIsTaken(@Parameter(description = "유저 ID", example = "1") @PathVariable Long userId, @RequestBody @Valid ScheduleRequestDTO.UpdateIsTakenDTO request, @Parameter(description = "스케쥴 ID", example = "1") @PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.updateSchedule(userId, scheduleId, request);
        return ApiResponse.onSuccess(ScheduleConverter.toUpdateScheduleResultDTO(schedule));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "복용 일정 조회 API", description = "복용 일정 조회 API(날짜 기반)")
    public ApiResponse<Map<String, Map<String, ScheduleResponseDTO.GetScheduleResultListDTO>>> getSchedule(@Parameter(description = "스케쥴 검색 날짜", example = "2025-06-02") @RequestParam @NotNull(message = " 값은 필수 입력 값입니다.") LocalDate scheduleDate,
                                                                                                           @Parameter(description = "유저 ID", example = "1") @PathVariable Long userId) {
        Map<String, Map<String, ScheduleResponseDTO.GetScheduleResultListDTO>> scheduleList = scheduleService.getScheduleList(userId, scheduleDate);
        return ApiResponse.onSuccess(scheduleList);
    }

    @DeleteMapping("/{scheduleId}/users/{userId}")
    @Operation(summary = "복용 일정 삭제 API", description = "복용 일정 삭제 API")
    public ApiResponse<?> deleteSchedule(@Parameter(description = "스케쥴 ID", example = "1") @PathVariable Long scheduleId,
                                         @Parameter(description = "유저 ID", example = "1") @PathVariable Long userId){
        scheduleService.deleteSchedule(userId, scheduleId);
        return ApiResponse.onSuccess(null);
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "복용 일정 전체 삭제 API", description = "복용 일정 전체 삭제 API")
    public ApiResponse<?> deleteAllSchedule(@Parameter(description = "유저 ID", example = "1") @PathVariable Long userId){
        scheduleService.deleteAllSchedule(userId);
        return ApiResponse.onSuccess(null);
    }
}
