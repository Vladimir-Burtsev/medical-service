package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface DiseaseSampleRepository extends JpaRepository<DiseaseSample, Long> {

    @Query("SELECT ds FROM DiseaseSample ds WHERE ds.doctor.id = :doctorId AND ds.diseaseDep.id = :diseaseDepId")
    Set<DiseaseSample> getDiseaseSampleByDoctorIdAndAndDiseaseDepId(@Param("doctorId") long doctorId,
                                                                    @Param("diseaseDepId") long diseaseDepId);
}
