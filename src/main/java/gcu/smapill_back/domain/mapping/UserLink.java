package gcu.smapill_back.domain.mapping;

import gcu.smapill_back.domain.User;
import gcu.smapill_back.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLink extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dependent_id", nullable = false)
    private User dependent;

    @ManyToOne
    @JoinColumn(name = "protector_id", nullable = false)
    private User protector;
}
