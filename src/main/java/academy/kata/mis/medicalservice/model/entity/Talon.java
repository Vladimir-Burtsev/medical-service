//package academy.kata.mis.medicalservice.model;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
///**
// * Талон на прием к врачу
// */
//@Entity
//@Table(name = "talons")
//public class Talon {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    /**
//     * дата и время записи на прием
//     */
//    @Column(name = "time", nullable = false)
//    private LocalDateTime time;
//
//    /**
//     * связь талона с пациентом
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "patient_id")
//    private Patient patient;
//
//    /**
//     * связь талона с доктором
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "doctor_id", nullable = false)
//    private Doctor doctor;
//}
