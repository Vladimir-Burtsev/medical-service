//package academy.kata.mis.medicalservice.model;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.util.List;
//
///**
// * Посещение
// */
//@Entity
//@Table(name = "visits")
//public class Visit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    /**
//     * дата посещения
//     */
//    @Column(name = "day_of_visit", nullable = false)
//    private LocalDate dayOfVisit;
//
//    /**
//     * доктор, который принял пациента
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "doctor_id", nullable = false)
//    private Doctor doctor;
//
//    /**
//     * связь с обращением по заболеванию
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "appeal_id", nullable = false)
//    private Appeal appeal;
//
//    /**
//     * услуги, которые были оказаны пациенту в рамках этого посещения
//     */
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "visit_medical_services_dep",
//            joinColumns = @JoinColumn(name = "visit_id"),
//            inverseJoinColumns = @JoinColumn(name = "medical_service_dep_id"))
//    private List<MedicalServiceDep> medicalServicesDep;
//
//}
