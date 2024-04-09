package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
