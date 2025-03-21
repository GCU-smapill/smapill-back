package gcu.smapill_back.domain;

import gcu.smapill_back.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Device extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "device_type", nullable = false, length = 255)
    private String deviceType;

    @Column(name = "device_ip", nullable = false, length = 40)
    private String deviceIp;

    public void setUser(User user) {
        this.user = user;
    }
}
