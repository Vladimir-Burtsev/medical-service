package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("""
            SELECT d.department.id FROM Doctor d WHERE d.id = :id
            """)
    long getDepartmentIdByDoctorId(long id);

    @Query("""
            SELECT t.doctor.department.id FROM Talon t WHERE t.id = :talonId
            """)
    Long getDepartmentIdByTalonId(Long talonId);
}
