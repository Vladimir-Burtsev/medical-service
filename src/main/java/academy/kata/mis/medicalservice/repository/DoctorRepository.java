package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByUserId(UUID doctorId);

    @Query("SELECT CASE WHEN COUNT(d) > 0 AND (SELECT d2.id FROM Doctor d2 WHERE d2.userId = :doctorId) = :id " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Doctor d WHERE d.userId = :doctorId")
    boolean existsByUserIdAndId(@Param("doctorId") UUID doctorId, @Param("id") long id);
}
