package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.MedicalServiceDep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalServiceDepRepository extends JpaRepository<MedicalServiceDep, Long> {
}
