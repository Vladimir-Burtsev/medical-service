package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.entity.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface DiseaseService {
    Disease getById(long diseaseId);

    long getDiseaseByDiseaseDepId(long diseaseDepId);

    Page<DiseaseShortInfoDto> getDiseaseShortInfoPagination(@Param("departmentId") Long departmentId,
                                                            @Param("diseaseName") String diseaseName,
                                                            @Param("identifier") String identifier,
                                                            @Param("order") String orderBy,
//                                                            @Param("pageSize") long pageSize,
//                                                            @Param("pageNumber") long pageNumber,
                                                            Pageable pageable);
}
