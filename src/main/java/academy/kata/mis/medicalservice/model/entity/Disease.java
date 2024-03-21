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
 * Disease Заболевание
 * классификатор.
 */
@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * идентификатор заболевания
     */
    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    /**
     * название заболевания
     */
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "disease")
    private Set<DiseaseDep> diseasesDep;
}
