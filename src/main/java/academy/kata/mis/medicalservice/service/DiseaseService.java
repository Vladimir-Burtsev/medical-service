package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.entity.Disease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiseaseService {
    Disease getById(long diseaseId);
    long getDiseaseByDiseaseDepId(long diseaseDepId);
    Page<DiseaseShortInfoDto> getDiseaseShortInfoPagination(long doctorId,
                                                            String diseaseName,
                                                            String identifier,
                                                            Pageable pageable);
}
