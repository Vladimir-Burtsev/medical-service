package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.service.DiseaseDepBusinessService;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiseaseDepBusinessServiceImpl implements DiseaseDepBusinessService {

    private final DiseaseDepService diseaseDepService;

    @Override
    public void checkDiseaseDepExist(long diseaseDepId) {
        if (!diseaseDepService.isExistById(diseaseDepId)) {
            log.error(String.format("Заболевание с id=%s, не найдено", diseaseDepId));
            throw new LogicException("Заболевание не найдено");
        }
    }

    @Override
    public long getDiseaseDepDepartmentId(long diseaseDepId) {
        return diseaseDepService.getDiseaseDepDepartmentId(diseaseDepId);
    }

}
