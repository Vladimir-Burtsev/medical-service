package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;


@Entity
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
}
