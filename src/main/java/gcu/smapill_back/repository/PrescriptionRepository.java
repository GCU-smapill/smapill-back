package gcu.smapill_back.repository;

import gcu.smapill_back.domain.Prescription;
import gcu.smapill_back.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}