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
    @Query("DELETE FROM UserLink ul WHERE ul.protector.userId = :userId")
    void deleteAllByProtectorUserId(@Param("userId") String userId);

    @Modifying
    @Query("DELETE FROM UserLink ul WHERE ul.dependent.userId = :userId")
    void deleteAllByDependentUserId(@Param("userId") String userId);
}
