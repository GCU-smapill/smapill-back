package gcu.smapill_back.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class DeviceResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceJoinResultDTO {
        Long id;
        String deviceIp;
        LocalDateTime createdAt;
    }
}