package gcu.smapill_back.web.controller;

import gcu.smapill_back.apiPayload.exception.ApiResponse;
import gcu.smapill_back.config.auth.UserDetail;
import gcu.smapill_back.converter.DeviceConverter;
import gcu.smapill_back.converter.UserConverter;
import gcu.smapill_back.domain.Device;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.service.DeviceService;
import gcu.smapill_back.web.dto.DeviceRequestDTO;
import gcu.smapill_back.web.dto.DeviceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
@Tag(name = "device", description = "기기 API")
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/")
    @Operation(summary = "기기등록 API", description = "기기등록 API")
    public ApiResponse<DeviceResponseDTO.DeviceJoinResultDTO> accessDevice(@Valid @AuthenticationPrincipal UserDetail userDetail, @RequestBody DeviceRequestDTO.DeviceAccessDTO request) throws Exception {
        Device device = deviceService.accessDevice(userDetail.getUser().getId(), request);
        return ApiResponse.onSuccess(DeviceConverter.toDeviceResultDTO(device));
    }
}
