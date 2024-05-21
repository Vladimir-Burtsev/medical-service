package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAllByUserId(UUID userId);

    Boolean existsPatientByUserIdAndOrganizationId(UUID userId, long organizationId);

    Patient getPatientById(long patientId);

    @Query("""
        SELECT CASE WHEN (COUNT(p.id) > 0) THEN true ELSE false END
            FROM Patient p
            JOIN Doctor d ON p.organization.id = d.department.organization.id
            WHERE p.id = :patientId AND d.id = :doctorId
            """)
    boolean isPatientExistsAndFromSameOrganizationAsDoctor(long patientId, long doctorId);

    @Query("select p.userId from Patient p where p.id =:id")
    UUID findUserIdByPatientId(long id);

    @Query("""
        select case when (count(p.id) > 0 ) then true else false end 
        from Patient p 
        where  p.id = :id and p.userId = cast(:userId as uuid)
        """)
    boolean isPatientExistAndUserIdIsPatientUserId(long id, String userId);
}
