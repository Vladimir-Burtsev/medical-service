package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.DiseaseDepBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiseaseDepBusinessServiceImpl implements DiseaseDepBusinessService {
    private final DiseaseDepService diseaseDepService;

    @Override
    public boolean checkIsExistByIdAndDoctorId(long diseaseDepId, long doctorId) {
        return diseaseDepService.checkIsExistByIdAndDoctorId(diseaseDepId, doctorId);
    }
}
