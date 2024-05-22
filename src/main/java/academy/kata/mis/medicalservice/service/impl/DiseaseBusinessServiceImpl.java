package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.DiseaseBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseBusinessServiceImpl implements DiseaseBusinessService {
    DiseaseService diseaseService;
    @Override
    public String getDiseaseIdentifier(long diseaseDepId) {
        return diseaseService.getById(diseaseDepId).getIdentifier();
    }
}
