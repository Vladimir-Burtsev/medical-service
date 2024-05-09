package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiseaseDepRepository extends JpaRepository<DiseaseDep, Long> {
    @Query("""
            select count (dd.id)
            from DiseaseDep dd
                join Department dep on dep.id = dd.department.id
                join Doctor doc on doc.department.id = dep.id
            where doc.id = :doctorId
            and dd.id = :diseaseDepId
    """)
    long checkCountExistByIdAndDoctorId(long diseaseDepId, long doctorId);
}
