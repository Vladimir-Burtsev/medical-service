package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * пациент
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Patient patient = (Patient) o;
        return getId() != null && Objects.equals(getId(), patient.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
