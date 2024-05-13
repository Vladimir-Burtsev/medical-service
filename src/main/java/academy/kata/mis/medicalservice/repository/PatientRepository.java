package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByUserId(UUID userId);
    Boolean existsPatientByUserIdAndOrganizationId(UUID userId,long organizationId);

    Patient getPatientById(long patientId);

    @Query("select p.userId from Patient p where p.id =:id")
    Optional<String> findUserIdByPatientId(long id);

}
