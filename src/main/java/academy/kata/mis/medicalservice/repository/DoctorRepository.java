package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByUserId(UUID doctorId);

    List<Doctor> findAllByUserId(UUID userId);

    @Query("SELECT CASE WHEN COUNT(d) > 0 AND (SELECT d2.id FROM Doctor d2 WHERE d2.userId = :doctorId) = :id " +
            "THEN TRUE ELSE FALSE END " +
            "FROM Doctor d WHERE d.userId = :doctorId")
    boolean existsByUserIdAndId(@Param("doctorId") UUID doctorId, @Param("id") long id);

    @Query("""
            SELECT t.doctor.id
            FROM Talon t
            WHERE t.id = :talonId
            """)
    Long getDoctorIdByTalonId(@Param("talonId") Long talonId);

    @Query("""
            SELECT d.personId
            FROM Talon t LEFT JOIN Doctor d ON t.doctor.id = d.id
            WHERE t.id = :talonId
            """)
    Long getDoctorPersonIdByTalonId(@Param("talonId") Long talonId);

    @Query("""
            SELECT t.organization.id
            FROM Department t
            WHERE t.id = :departmentId
            """)
    Long getOrganizationIdByDepartmentId(@Param("departmentId") long departmentId);

}
