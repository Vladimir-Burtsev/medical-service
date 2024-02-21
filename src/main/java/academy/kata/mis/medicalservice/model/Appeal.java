package academy.kata.mis.medicalservice.model;

import academy.kata.mis.medicalservice.enums.AppealStatus;
import academy.kata.mis.medicalservice.enums.InsuranceType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Appeal - Обращение
 * <p>
 * Пациент обращается с заболеванием. И лечат заболевание.
 * При первом посещении устанавливается заболевание, которое будут лечить.
 * Обращение может лечиться как в одно посещение, так и в несколько.
 * Врач, оказывающий услуги в рамках одного обращения, может меняться(заболел), но не должна меняться специализация врача.
 * Любой из врачей, участвовавших в лечении пациента, может закрыть/открыть обращение.
 * Пока обращение не закрыто, врач может его править (только те посещения, где лечил он).
 * Когда обращение закрыто, оно может попасть в счет и пока оно в счете, его нельзя править никому.(isClosed = true)
 * Обращение может быть удалено из сформированного счета для модификации.
 * Любой из врачей, участвовавших в лечении пациента может открыть обращение для устранения ошибок, если оно не в счете.
 * Закрытое обращение нельзя модифицировать
 */
@Entity
@Table(name = "appeals")
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Счет с которым может быть связано обращение.
     * информация о счете в mis-economic-service
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * статус
     */
    @Column(name = "status", nullable = false)
    private AppealStatus status;

    /**
     * тип страхования
     */
    @Column(name = "insurance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    /**
     * Дата создания обращения.
     */
    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    /**
     * Дата закрытия обращения.
     */
    @Column(name = "closed_date")
    private LocalDate closedDate;

    /**
     * посещения во время которых лечилось это заболевание
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appeal")
    private Set<Visit> visits;

    /**
     * посещения во время которых лечилось это заболевание
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appeal")
    private Set<XRay> xRays;

    /**
     * пациент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    /**
     * заболевание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_dep_id", nullable = false)
    private DiseaseDep diseaseDep;

}
