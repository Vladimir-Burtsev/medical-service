package academy.kata.mis.medicalservice.model.entity;

import academy.kata.mis.medicalservice.model.enums.MedicalServiceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;

/**
 * MedicalServiceDep - Медицинская услуга которые оказывает отделение. Взяты из справочника
 * Заведующий создает список услуг которые может оказывать его отделение
 */
@Entity
@Table(name = "medical_services_dep")
public class MedicalServiceDep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * статус услуги
     */
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
    @JoinColumn(name = "medical_service", nullable = false)
    private MedicalService medicalService;

    /**
     * услуга может использоваться в разных посещениях
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "medicalServicesDep")
    private List<Visit> visits;
}
