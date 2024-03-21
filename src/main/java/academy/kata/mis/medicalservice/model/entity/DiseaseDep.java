package academy.kata.mis.medicalservice.model.entity;

import academy.kata.mis.medicalservice.model.enums.DiseaseStatus;
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

import java.util.Set;

/**
 * DiseaseDep - Заболевание которое лечит отделение взятые из справочника.
 * Заведующий отделением наполняет список заболеваний каждого отделения из справочника.
 */
@Entity
@Table(name = "diseases_dep")
public class DiseaseDep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * статус заболевания
     */
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
}
