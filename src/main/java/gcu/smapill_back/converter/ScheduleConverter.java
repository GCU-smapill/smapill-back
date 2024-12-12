package gcu.smapill_back.converter;

import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.enums.TimeOfDay;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

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
}
