package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

/**
 * Черновик заболеваний доктора.
 * Доктор создает черновик для заболеваний которые он может лечить в отделении
 * и связывает с ним услуги которые может оказывать его отделение
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disease_samples")
public class DiseaseSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * название шаблона
     */
    @Column(name = "name", unique = true)
    private String name;

    /**
     * описание шаблона
     */
    @Column(name = "description")
    private String description;

    /**
     * ссылка на доктора
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    /**
     * ссылка на заболевание которое лечит отделение доктора
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_dep_id", nullable = false)
    private DiseaseDep diseaseDep;

    /**
     * медицинские услуги отделения которые доктор использует как шаблон для лечения заболевания
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "disease_samples_medical_services_dep",
            joinColumns = @JoinColumn(name = "disease_sample_id"),
            inverseJoinColumns = @JoinColumn(name = "medical_service_dep_id"))
    private Set<MedicalServiceDep> servicesDep;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DiseaseSample that = (DiseaseSample) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
