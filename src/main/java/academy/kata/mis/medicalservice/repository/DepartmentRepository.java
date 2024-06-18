package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    //novikov
    @Query("""
            SELECT d.department.id FROM Doctor d WHERE d.id = :id
            """)
    long getDepartmentIdByDoctorId(long id);
}
