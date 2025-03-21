package gcu.smapill_back.converter;

import gcu.smapill_back.domain.Device;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.web.dto.DeviceRequestDTO;
import gcu.smapill_back.web.dto.DeviceResponseDTO;
import gcu.smapill_back.web.dto.UserRequestDTO;
import gcu.smapill_back.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

public class DeviceConverter {
    public static Device toAccessResultDTO(DeviceRequestDTO.DeviceAccessDTO request) {
        return Device.builder()
                .deviceType(request.getDeviceType())
                .deviceIp(request.getDeviceIp())
                .build();
    }

    public static DeviceResponseDTO.DeviceJoinResultDTO toDeviceResultDTO(Device device) {
        return DeviceResponseDTO.DeviceJoinResultDTO.builder()
                .id(device.getId())
                .deviceIp(device.getDeviceIp())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
