package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseDepShortInfoResponse;
import academy.kata.mis.medicalservice.model.enums.DiseaseOrder;
import org.springframework.data.domain.Pageable;

public interface DiseaseBusinessService {
    String getDiseaseIdentifier(long diseaseDepId);
    GetDiseaseDepShortInfoResponse getDiseaseDepShortInfoResponse(long doctorId,
                                                                  String diseaseName,
                                                                  String identifier,
                                                                  DiseaseOrder orderBy,
                                                                  int page,
                                                                  int size);
}
