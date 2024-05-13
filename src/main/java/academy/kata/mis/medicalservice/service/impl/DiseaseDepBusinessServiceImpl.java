package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.DiseaseDepBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseDepBusinessServiceImpl implements DiseaseDepBusinessService {
    private final DiseaseDepService diseaseDepService;

    @Override
    public boolean checkIsExistByIdAndDoctorId(long diseaseDepId, long doctorId) {
        return diseaseDepService.checkIsExistByIdAndDoctorId(diseaseDepId, doctorId);
    }
}
