package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.entity.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    Disease getDiseaseById(long diseaseId);

    @Query("""
            SELECT dd.id, d.name, d.identifier
            FROM DiseaseDep dd JOIN Disease d ON dd.disease.id = d.id
            WHERE dd.department.id = :departmentId AND dd.status = 'OPEN'
                AND d.name LIKE :diseaseName%
                AND d.identifier LIKE :identifier%
            ORDER BY :orderBy
            """)
//            LIMIT :pageSize
//            OFFSET :pageNumber
//            """)
    Page<DiseaseShortInfoDto> getDiseaseShortInfoPagination(@Param("departmentId") Long departmentId,
                                                            @Param("diseaseName") String diseaseName,
                                                            @Param("identifier") String identifier,
                                                            @Param("order") String orderBy,
//                                                            @Param("pageSize") long pageSize,
//                                                            @Param("pageNumber") long pageNumber,
                                                            Pageable pageable);
}

/*

            int pageNumber = 2;
            int offset = (pageNumber - 1) * pageSize
            @RequestParam(name = "doctor_id") long doctorId,
            @RequestParam(name = "disease_name", required = false, defaultValue = "") long diseaseName,
            @RequestParam(name = "identifier", required = false, defaultValue = "") long identifier,
            @RequestParam(name = "order", required = false, defaultValue = "IDENTIFIER_ASC") DiseaseOrder order,
            @RequestParam(name = "page", required = false, defaultValue = "1") long page,
            @RequestParam(name = "size", required = false, defaultValue = "10") long size) {
 */

