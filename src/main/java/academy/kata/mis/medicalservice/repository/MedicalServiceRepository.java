package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.MedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {
}
