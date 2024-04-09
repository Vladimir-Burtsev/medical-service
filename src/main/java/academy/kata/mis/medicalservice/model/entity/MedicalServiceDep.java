package academy.kata.mis.medicalservice.model.entity;

import academy.kata.mis.medicalservice.model.enums.MedicalServiceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

/**
 * MedicalServiceDep - Медицинская услуга которые оказывает отделение. Взяты из справочника
 * Заведующий создает список услуг которые может оказывать его отделение
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_services_dep")
public class MedicalServiceDep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * статус услуги
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MedicalServiceStatus status;

    /**
     * связь услуги с отделением
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /**
     * связь услуги со справочником
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_service_id", nullable = false)
    private MedicalService medicalService;

    /**
     * услуга может использоваться в разных посещениях
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "medicalServicesDep")
    private Set<Visit> visits;

    /**
     * услуга может использоваться в разных шаблонах докторов
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "servicesDep")
    private Set<DiseaseSample> doctorSamples;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        MedicalServiceDep that = (MedicalServiceDep) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
