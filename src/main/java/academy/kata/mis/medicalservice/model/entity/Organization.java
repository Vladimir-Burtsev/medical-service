package academy.kata.mis.medicalservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Department> departments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
    private Set<Patient> patients;
}
