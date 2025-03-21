package gcu.smapill_back.web.dto;

import gcu.smapill_back.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class DeviceRequestDTO {
    @Getter
    public static class DeviceAccessDTO {
        @NotNull(message = "기기명을 입력하세요")
        private String deviceType;

        @NotNull(message = "기기ip를 입력하세요")
        private String deviceIp;
    }
}
