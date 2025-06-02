package gcu.smapill_back.repository;

import gcu.smapill_back.domain.mapping.UserLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLinkRepository extends JpaRepository<UserLink, Long> {
    List<UserLink> findAllByProtectorId(Long protectorId);
    List<UserLink> findAllByDependentId(Long dependentId);
    Optional<UserLink> findByProtectorIdAndDependentId(Long protectorId, Long dependentId);

    @Modifying
    @Query("DELETE FROM UserLink ul WHERE ul.protector.id = :id")
    void deleteAllByProtectorId(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM UserLink ul WHERE ul.dependent.id = :id")
    void deleteAllByDependentId(@Param("id") Long id);
}
