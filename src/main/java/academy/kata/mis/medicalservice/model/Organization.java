package academy.kata.mis.medicalservice.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

/**
 * Медицинская организация
 */
@Entity
@Getter
@Table(name = "organizations")
public class Organization {

    /**
     * Соответствует id организации из mis-structure-service
     */
    @Id
    private long id;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
//    private Set<Department> departments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Patient> patients;
}
