package gcu.smapill_back.web.dto;

import gcu.smapill_back.domain.enums.TimeOfDay;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

public class ScheduleRequestDTO {
    @Getter
    public static class CreateScheduleDTO {

        @NotNull(message = "약 명칭을 입력하세요")
        @Length(max = 40)
        private String name;

        @NotNull(message = "복용 시작 날짜를 입력하세요")
        private LocalDate startDate;

        @NotNull(message = "복용 종료 날짜를 입력하세요")
        private LocalDate endDate;

        @NotNull(message = "복용 시간을 입력하세요")
        private List<TimeOfDay> intakeTimes;

        @NotNull(message = "복용량을 입력하세요")
        private String dosage;
    }

    @Getter
    public static class UpdateIsTakenDTO {
        @NotNull(message = "복용 여부를 입력하세요")
        private Boolean isTaken;
    }
}
