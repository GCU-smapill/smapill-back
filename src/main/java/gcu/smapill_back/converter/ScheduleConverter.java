package gcu.smapill_back.converter;

import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.enums.TimeOfDay;
import gcu.smapill_back.web.dto.PrescriptionRequestDTO;
import gcu.smapill_back.web.dto.PrescriptionResponseDTO;
import gcu.smapill_back.web.dto.ScheduleRequestDTO;
import gcu.smapill_back.web.dto.ScheduleResponseDTO;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ScheduleConverter {
    public static Schedule toSchedule(String name, LocalDate currentDate, TimeOfDay timeOfDay, Prescription prescription) {
        return Schedule.builder()
                .name(name)
                .scheduleDate(currentDate)
                .timeOfDay(timeOfDay)
                .prescription(prescription)
                .build();
    }

    public static Schedule toScheduleResultDTO(ScheduleRequestDTO.CreateScheduleDTO request) {
        return Schedule.builder()
                .name(request.getName())
                .scheduleDate(request.getScheduleDate())
                .timeOfDay(request.getTimeOfDay())
                .build();
    }

    public static ScheduleResponseDTO.CreateScheduleResultDTO toCreateResultDTO(Schedule schedule) {
        return ScheduleResponseDTO.CreateScheduleResultDTO.builder()
                .scheduleId(schedule.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ScheduleResponseDTO.UpdateScheduleResultDTO toUpdateScheduleResultDTO(Schedule schedule) {
        return ScheduleResponseDTO.UpdateScheduleResultDTO.builder()
                .scheduleId(schedule.getId())
                .isTaken(schedule.isTaken())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
}
