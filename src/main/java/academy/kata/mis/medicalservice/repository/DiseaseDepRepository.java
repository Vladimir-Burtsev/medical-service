package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseDepRepository extends JpaRepository<DiseaseDep, Long> {
}
