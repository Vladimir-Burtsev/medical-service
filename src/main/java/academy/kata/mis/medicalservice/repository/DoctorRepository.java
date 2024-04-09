package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
