package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.DiseaseBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseBusinessServiceImpl implements DiseaseBusinessService {
    private final DiseaseService diseaseService;
    @Override
    public String getDiseaseIdentifier(long diseaseDepId) {
        long diseaseId = diseaseService.getDiseaseByDiseaseDepId(diseaseDepId);
        return diseaseService.getById(diseaseId).getIdentifier();
    }
}
