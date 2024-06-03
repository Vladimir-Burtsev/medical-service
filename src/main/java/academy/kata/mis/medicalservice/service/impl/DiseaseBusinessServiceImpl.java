package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseDepShortInfoResponse;
import academy.kata.mis.medicalservice.model.enums.DiseaseOrder;
import academy.kata.mis.medicalservice.service.DiseaseBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseBusinessServiceImpl implements DiseaseBusinessService {
    private final DiseaseService diseaseService;

    @Override
    public GetDiseaseDepShortInfoResponse getDiseaseDepShortInfoResponse(long doctorId,
                                                                         String diseaseName,
                                                                         String identifier,
                                                                         DiseaseOrder orderBy,
                                                                         int page,
                                                                         int size) {
        return new GetDiseaseDepShortInfoResponse(
                diseaseService.getDiseaseShortInfoPagination(
                        doctorId, diseaseName, identifier,
                        PageRequest.of(page - 1, size, orderBy.getOrderBy())
                ).getContent());
    }
}
