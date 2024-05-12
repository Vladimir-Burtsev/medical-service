package academy.kata.mis.medicalservice.model.dto.disease.convertor;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import org.springframework.stereotype.Component;

@Component
public class DiseaseConvertor {

    public DiseaseShortInfoDto entityToDiseaseShortInfoDto(DiseaseDep diseaseDep) {
        return DiseaseShortInfoDto.builder()
                .diseaseDepId(diseaseDep.getId())
                .diseaseName(null)
                .diseaseIdentifier(null)
                .build();
    }
}
