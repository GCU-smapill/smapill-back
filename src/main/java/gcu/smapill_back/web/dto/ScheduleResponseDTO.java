package gcu.smapill_back.web.dto;

import gcu.smapill_back.domain.enums.TimeOfDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateScheduleResultDTO {
        Long scheduleId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateScheduleResultListDTO {
        List<CreateScheduleResultDTO> schedules;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateScheduleResultDTO {
        Long scheduleId;
        Boolean isTaken;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetScheduleResultDTO {
        Long scheduleId;
        String name;
        Boolean isTaken;
        String dosage;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetScheduleResultListDTO {
        List<GetScheduleResultDTO> schedule;
    }
}
