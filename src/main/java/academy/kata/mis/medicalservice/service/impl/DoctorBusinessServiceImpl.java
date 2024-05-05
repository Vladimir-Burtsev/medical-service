package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.entity.Department;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.enums.DiseaseStatus;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class DoctorBusinessServiceImpl implements DoctorBusinessService {

    @Override
    public void isDiseaseDepExistsAndMatchesDoctorDepartment(Long diseaseDepId, Department department) {
                Set<DiseaseDep> diseasesDepList = department.getDiseasesDep();
        DiseaseDep diseasesDep = diseasesDepList.stream()
                .filter(diseaseDep -> diseaseDep.getStatus().equals(DiseaseStatus.OPEN)
                        && diseaseDep.getId().equals(diseaseDepId))
                .findFirst()
                .orElse(null);
        if (diseasesDep == null) {
            log.error("Заболевание отделения с id:{}; не найдено или заболевание не совпадает с отделением доктора.", diseaseDepId);
            throw new LogicException("Заболевание не найдено или не совпадает с отделением доктора.");
        }
    }
}
