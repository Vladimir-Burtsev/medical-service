package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseDepServiceImpl implements DiseaseDepService {

    private final DiseaseDepRepository diseaseDepRepository;

    @Override
    public boolean isExistById(long diseaseDepId) {
        return diseaseDepRepository.existsById(diseaseDepId);
    }

}
