package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorPatientInfoDto;
import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface TalonRepository extends JpaRepository<Talon, Long> {
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID id);

    @Query("""
        select t
        from Talon t
        join fetch t.doctor
        where t.patient.id = :patientId
        """)
    Set<Talon> findAllByPatientId(long patientId);

    @Query("""
            SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END
            FROM Talon t
            WHERE t.id = :talonId AND t.doctor.userId = :userId
            """)
    boolean isDoctorAssignToTalonByUserIdAndTalonId(UUID userId, Long talonId);

    @Query("""
           SELECT NEW academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorPatientInfoDto(
                t.id,
                t.doctor.id,
                t.doctor.positionId,
                t.doctor.personId,
                p.id,
                p.personId,
                t.time
           )
           FROM Talon t
            LEFT OUTER JOIN t.patient p
           WHERE t.id = :talonId
           """)
    TalonWithDoctorPatientInfoDto getTalonWithDoctorPatientPersonsById(Long talonId);
}
