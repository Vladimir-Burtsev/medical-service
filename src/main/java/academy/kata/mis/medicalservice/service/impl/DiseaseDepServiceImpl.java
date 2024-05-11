package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiseaseDepServiceImpl implements DiseaseDepService {

    private final DiseaseDepRepository diseaseDepRepository;

    @Override
    public DiseaseDep findDiseaseDepById(long diseaseDepId) {
        Optional<DiseaseDep> optionalDiseaseDep = diseaseDepRepository.findById(diseaseDepId);
        if (optionalDiseaseDep.isPresent()) {
            return optionalDiseaseDep.get();
        } else throw new LogicException("DiseaseDep с id=" + diseaseDepId + " не найденo");
    }

    @Override
    public boolean isExistById(long diseaseDepId) {
        return diseaseDepRepository.existsById(diseaseDepId);
    }
}
