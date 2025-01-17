package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
            SELECT d FROM Doctor d
            WHERE d.userId = :doctorUUID
            order by d.id limit 1
            """)
    Doctor findByUserId(UUID doctorUUID);

    List<Doctor> findAllByUserId(UUID userId);

    @Query("""
            SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END
            FROM Doctor d WHERE d.id = :id AND d.userId = :doctorId
            """)
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
            SELECT d FROM Doctor d
            JOIN FETCH d.department dep
            JOIN FETCH dep.organization org
            WHERE d.userId = :userId
            """)
    List<Doctor> findAllWithDepartmentsAndOrganizations(@Param("userId") UUID userId);

    @Query("""
            SELECT case when (count(d.id) > 0) then true else false
            END from Doctor d where d.id = :id
            """)
    boolean isDoctorExistsById(Long id);

    @Query("""
            SELECT d.positionId FROM Doctor d WHERE d.id = :id
            """)
    long getPositionIdByDoctorId(long id);

    @Query("""
            SELECT d.personId FROM Doctor d WHERE d.id = :doctorId
            """)
    Long getPersonIdByDoctorId(Long doctorId);

    @Query("""
            SELECT CASE WHEN EXISTS (
                SELECT 1
                FROM Visit v
                JOIN Doctor d1 ON v.doctor.id = d1.id
                JOIN Doctor d2 ON d1.department.id = d2.department.id
                WHERE v.id = :visitId AND d2.userId = :doctorUUID
            ) THEN true ELSE false END
            """)
    boolean areDoctorsInSameDepartment(@Param("visitId") long visitId, @Param("doctorUUID") UUID doctorUUID);
}
