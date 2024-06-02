package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.entity.Disease;
import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.repository.DiseaseRepository;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final DiseaseDepRepository diseaseDepRepository;

    @Override
    public Disease getById(long diseaseId) {
        return diseaseRepository.getReferenceById(diseaseId);
    }

    @Override
    public long getDiseaseByDiseaseDepId(long diseaseDepId) {
        return diseaseDepRepository.findDiseaseIdByDiseaseDepId(diseaseDepId);
    }

    @Override
    public Page<DiseaseShortInfoDto> getDiseaseShortInfoPagination(@Param("departmentId") Long departmentId,
                                                                   @Param("diseaseName") String diseaseName,
                                                                   @Param("identifier") String identifier,
                                                                   @Param("order") String orderBy,
//                                                            @Param("pageSize") long pageSize,
//                                                            @Param("pageNumber") long pageNumber,
                                                                   Pageable pageable) {
        return diseaseRepository.getDiseaseShortInfoPagination(departmentId,
                                                                diseaseName,
                                                                identifier,
                                                                orderBy,
                                                                pageable);
    }
}
