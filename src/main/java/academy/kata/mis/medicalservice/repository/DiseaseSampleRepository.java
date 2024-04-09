package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseSampleRepository extends JpaRepository<DiseaseSample, Long> {
}
