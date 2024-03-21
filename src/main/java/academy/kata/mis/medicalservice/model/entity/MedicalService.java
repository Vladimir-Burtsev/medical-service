package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

/**
 * MedicalService - Медицинская услуга.
 * справочник.
 */
@Entity
@Table(name = "medical_services")
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * уникальный идентификатор услуги
     */
    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    /**
     * название услуги
     */
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalService")
    private Set<MedicalServiceDep> servicesDep;
}
