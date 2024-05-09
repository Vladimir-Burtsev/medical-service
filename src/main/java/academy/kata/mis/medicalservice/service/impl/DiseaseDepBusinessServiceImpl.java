package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.repository.DiseaseDepRepository;
import academy.kata.mis.medicalservice.service.DiseaseDepBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseDepBusinessServiceImpl implements DiseaseDepBusinessService {
    private final DiseaseDepRepository diseaseDepRepository;
    @Override
    public boolean checkIsExistByIdAndDoctorId(long diseaseDepId, long doctorId) {
        return diseaseDepRepository.checkCountExistByIdAndDoctorId(diseaseDepId, doctorId) > 0;
    }
}
