package gcu.smapill_back.domain;

import gcu.smapill_back.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Prescription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "frequency")
    private Integer frequency;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "instruction", length = 255)
    private String instruction;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;

    public void setUser(User user) {
        this.user = user;
        user.getPrescriptionList().add(this);
    }
}
