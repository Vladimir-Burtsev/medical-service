package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.entity.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    Disease getDiseaseById(long diseaseId);

    @Query("""
            SELECT new academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto(dd.id, d.name, d.identifier)
            FROM Disease d
                JOIN DiseaseDep dd ON dd.disease.id = d.id
                JOIN Doctor doc ON doc.department.id = dd.department.id
            WHERE doc.id = :doctorId
                AND dd.status = 'OPEN'
                AND d.name LIKE :diseaseName%
                AND d.identifier LIKE :identifier%
            """)
    Page<DiseaseShortInfoDto> getDiseaseShortInfoPagination(@Param("doctorId") long doctorId,
                                                            @Param("diseaseName") String diseaseName,
                                                            @Param("identifier") String identifier,
                                                            Pageable pageable);
}