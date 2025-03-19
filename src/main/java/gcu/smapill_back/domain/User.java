package gcu.smapill_back.domain;

import gcu.smapill_back.domain.common.BaseEntity;
import gcu.smapill_back.domain.enums.ModeStatus;
import gcu.smapill_back.domain.mapping.UserLink;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "email", nullable = false, length = 40)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="mode", nullable = false)
    private ModeStatus mode;

    @OneToMany(mappedBy = "dependent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLink> dependentList = new ArrayList<>();

    @OneToMany(mappedBy = "protector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLink> protectorList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Prescription> prescriptionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;

    public void encodePassword(String password) {
        this.password = password;
    }

    public void setMode(String mode) { this.mode = ModeStatus.valueOf(mode); }
}
