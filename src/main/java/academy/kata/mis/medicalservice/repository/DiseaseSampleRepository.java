package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface DiseaseSampleRepository extends JpaRepository<DiseaseSample, Long> {

    @Query("""
            SELECT sd.id
            FROM DiseaseSample ds
               JOIN ds.servicesDep sd
            WHERE ds.doctor.id = :doctorId
               AND ds.diseaseDep.id = :diseaseDepId
            """)
    Set<Long> getServiceDepIdByDoctorIdAndDiseaseDepId(long doctorId, long diseaseDepId);

    @Query("""
            SELECT ds.id
            FROM DiseaseSample ds
            WHERE ds.doctor.id = :doctorId
               AND ds.diseaseDep.id = :diseaseDepId
            """)
    Set<Long> getDiseaseSamplesIdByDoctorIdAndDiseaseId(long doctorId, long diseaseDepId);

    @Query("""
            SELECT sd.id
            FROM DiseaseSample ds
               JOIN ds.servicesDep sd
            WHERE ds.id = :diseaseSampleId
            """)
    Set<Long> getServiceDepIdByDiseaseSampleId(long diseaseSampleId);
}
