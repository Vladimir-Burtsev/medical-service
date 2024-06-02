package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DiseaseDepRepository extends JpaRepository<DiseaseDep, Long> {

    @Query("""
                select case when count(dd.id) > 0 then true else false end
                from DiseaseDep dd
                    join Department dep on dep.id = dd.department.id
                    join Doctor doc on doc.department.id = dep.id
                where doc.id = :doctorId
                    and dd.id = :diseaseDepId
            """)
    boolean checkIsExistByIdAndDoctorId(long diseaseDepId, long doctorId);

    @Query("select d.disease.id from DiseaseDep d where d.id = :diseaseDepId")
    long findDiseaseIdByDiseaseDepId(long diseaseDepId);
}