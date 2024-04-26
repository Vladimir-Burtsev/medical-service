package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TalonRepository extends JpaRepository<Talon, Long> {

    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID id);

//    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Talon t JOIN Patient p ON t.patient.id = p.id WHERE t.id=:talonId AND p.userId=:userId")
//    boolean existsTalonByIdAndPatientUserId(@Param("talonId") Long talonId, @Param("userId") UUID userId);

}
