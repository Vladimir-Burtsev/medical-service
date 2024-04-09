package academy.kata.mis.medicalservice.model.entity;

import academy.kata.mis.medicalservice.model.enums.DiseaseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

/**
 * DiseaseDep - Заболевание которое лечит отделение взятые из справочника.
 * Заведующий отделением наполняет список заболеваний каждого отделения из справочника.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diseases_dep")
public class DiseaseDep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * статус заболевания
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DiseaseStatus status;

    /**
     * отделение которое лечит это заболевание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /**
     * заболевание из спраавочника
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease disease;

    /**
     * обращения в которых лечили это заболевание
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diseaseDep")
    private Set<Appeal> appeals;

    /**
     * все шаблоны с заболеванием
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diseaseDep")
    private Set<DiseaseSample> diseaseSamples;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DiseaseDep that = (DiseaseDep) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
