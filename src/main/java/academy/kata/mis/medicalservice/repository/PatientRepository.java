package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByUserId(UUID userId);
    Boolean existsPatientByUserIdAndOrganizationId(UUID userId,long organizationId);

    Patient getPatientById(long patientId);
    Optional<Patient> findPatientById(long personId);
}
