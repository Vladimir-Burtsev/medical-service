package academy.kata.mis.medicalservice.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Медицинская организация
 */
@Entity
@Table(name = "organizations")
public class Organization {

    /**
     * Соответствует id организации из mis-structure-service
     */
    @Id
    private long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Department> departments;

}
