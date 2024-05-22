package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Disease;
import academy.kata.mis.medicalservice.repository.DiseaseRepository;
import academy.kata.mis.medicalservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {
    DiseaseRepository diseaseRepository;
    @Override
    public Disease getById(long diseaseId) {
        return diseaseRepository.getReferenceById(diseaseId);
    }
}
