package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiseaseDepRepository extends JpaRepository<DiseaseDep, Long> {
    @Query("SELECT d.department.id FROM DiseaseDep d WHERE d.id = :diseaseDepId")
    long getDoctorDepartmentId(@Param("diseaseDepId") long diseaseDepId);
}
