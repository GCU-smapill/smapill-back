package gcu.smapill_back.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class PrescriptionRequestDTO {
    @Getter
    public static class CreatePrescriptionDTO {

        @NotNull(message = "약 명칭을 입력하세요")
        @Length(max = 40)
        private String name;

        @NotBlank(message = "약 용량을 입력하세요")
        private String dosage;

        @NotNull(message = "하루 복용 횟수를 입력하세요")
        private Integer frequency;

        @NotNull(message = "복용 기간을 입력하세요")
        private Integer duration;

        private String instruction;

        @NotNull(message = "시작 날짜를 입력하세요")
        private LocalDate startDate;

        @NotNull(message = "종료 날짜를 입력하세요")
        private LocalDate endDate;
    }
}
