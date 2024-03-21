package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

/**
 * доктор
 */
@Entity
@Table(name = "doctors")
public class Doctor {

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
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /**
     * ид отделения доктора.
     * информация о отделении в mis-structure-service
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /**
     * все талоны доктора
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<Talon> talons;

    /**
     * все посещения на которых лечил доктор
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private List<Visit> visits;
}
