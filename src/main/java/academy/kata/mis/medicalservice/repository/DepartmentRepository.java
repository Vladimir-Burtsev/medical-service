package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {


}
