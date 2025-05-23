package gcu.smapill_back.repository;

import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.enums.TimeOfDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUserAndScheduleDateAndTimeOfDay(User user, LocalDate scheduleDate, TimeOfDay timeOfDay);

    List<Schedule> findAllByUser(User user);
}
