package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("select t from Talon t where cast(t.time as date) = current_date + 1")
    List<Talon> findAllByTomorrow();
}
