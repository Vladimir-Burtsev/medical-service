package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Disease;
import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.repository.DiseaseRepository;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;
    private final DiseaseDepRepository diseaseDepRepository;
    @Override
    public Disease getById(long diseaseId) {
        return diseaseRepository.getDiseaseById(diseaseId);
    }

    @Override
    public long getDiseaseByDiseaseDepId(long diseaseDepId) {
        return diseaseDepRepository.findDiseaseIdByDiseaseDepId(diseaseDepId);
    }
}
