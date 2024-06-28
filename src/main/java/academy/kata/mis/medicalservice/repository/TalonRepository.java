package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
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
            SELECT t.time
            FROM Talon t
            WHERE t.id = :talonId
            """)
    LocalDateTime getTalonTimeByTalonId(long talonId);

    @Query("""
            SELECT t.patient.personId
            FROM Talon t
            WHERE t.id = :talonId
            """)
    Optional<Long> getPatientPersonIdByTalonId(Long talonId);
}
