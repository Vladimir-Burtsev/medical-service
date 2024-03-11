//package academy.kata.mis.medicalservice.model;
//
//import javax.persistence.*;
//import java.util.List;
//
///**
// * доктор
// */
//@Entity
//@Table(name = "doctors")
//public class Doctor {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    /**
//     * соответствует id Персоны из микросервиса mis-person-service
//     */
//    @Column(name = "person_id", nullable = false)
//    private long personId;
//
//    /**
//     * Соответсвует id Пользователя из микросервиса mis-auth-service
//     */
//    @Column(name = "user_id", nullable = false)
//    private long userId;
//
//    /**
//     * ид отделения доктора.
//     * информация о отделении в mis-structure-service
//     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "department_id", nullable = false)
//    private Department department;
//
//    /**
//     * все талоны доктора
//     */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
//    private List<Talon> talons;
//
//    /**
//     * все посещения на которых лечил доктор
//     */
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctor")
//    private List<Visit> visits;
//}
