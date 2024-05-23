package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface TalonRepository extends JpaRepository<Talon, Long> {
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID id);

    @Query("SELECT d.personId FROM Talon t LEFT JOIN Doctor d ON t.doctor.id=d.id WHERE t.id=:talonId")
    Long getDoctorPersonIdByTalonId(@Param("talonId") Long talonId);

    @Query("""
        select t 
        from Talon t 
        join fetch t.doctor 
        where t.patient.id = :patientId
        """)
    Set<Talon> findAllByPatientId(long patientId);
}
