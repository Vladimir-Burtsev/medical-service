package academy.kata.mis.medicalservice.model;

import javax.persistence.*;
import java.util.Set;

/**
 * пациент
 */
@Entity
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
    private Long userId;

    /**
     * талоны на которые записан пациент
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<Talon> talons;

    /**
     * обращения пациента
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
    private Set<Appeal> appeals;
}
