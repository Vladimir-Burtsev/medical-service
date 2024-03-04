package academy.kata.mis.medicalservice.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

/**
 * пациент
 */
@Entity
@Getter
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * соответствует id Персоны из микросервиса mis-person-service
     */
    @Column(name = "person_id", nullable = false)
    private long personId;

    /**
     * Соответсвует id Пользователя из микросервиса mis-auth-service
     */
    @Column(name = "user_id")
    private UUID userId;

//    /**
//     * талоны на которые записан пациент
//     */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
//    private Set<Talon> talons;
//
//    /**
//     * обращения пациента
//     */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
//    private Set<Appeal> appeals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}
