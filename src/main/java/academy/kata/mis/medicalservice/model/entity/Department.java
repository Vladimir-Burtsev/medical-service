package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departments")
public class Department {

    /**
     * Соответствует id отделенеия из mis-structure-service
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * Доктора из отделения. Должно быть согласованно с mis-structure-service
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<Doctor> doctors;

    /**
     * Медрегистраторы из отделения
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<Registrar> registrars;

    /**
     * заболевания закрепленные за отделением
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<DiseaseDep> diseasesDep;

    /**
     * медицинские услуги закрепленные за отделением
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    private Set<MedicalServiceDep> servicesDep;

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
        Department that = (Department) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
