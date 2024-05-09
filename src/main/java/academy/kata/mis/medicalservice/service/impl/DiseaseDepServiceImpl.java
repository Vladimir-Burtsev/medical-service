package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import jakarta.persistence.EntityNotFoundException;
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
        } else throw new EntityNotFoundException("DiseaseDep с id=" + diseaseDepId + " не найденo");
    }
}
