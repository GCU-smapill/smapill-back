package gcu.smapill_back.repository;

import gcu.smapill_back.domain.Schedule;
import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.mapping.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {
    List<UserLink> findAllByProtectorId(Long protectorId);
    List<UserLink> findAllByDependentId(Long dependentId);
    Optional<UserLink> findByProtectorIdAndDependentId(Long protectorId, Long dependentId);
}
