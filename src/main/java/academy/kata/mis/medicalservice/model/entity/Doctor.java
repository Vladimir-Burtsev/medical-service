package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * доктор
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * соответствует id Позиции из микросервиса mis-structure-service
     */
    @Column(name = "position_id", nullable = false)
    private long positionId;

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
    private Set<Talon> talons;

    /**
     * все посещения на которых лечил доктор
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<Visit> visits;

    /**
     * все шаблоны доктора
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
    private Set<DiseaseSample> diseaseSamples;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Doctor doctor = (Doctor) o;
        return getId() != null && Objects.equals(getId(), doctor.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
