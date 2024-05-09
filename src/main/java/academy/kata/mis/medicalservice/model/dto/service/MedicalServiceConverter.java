package academy.kata.mis.medicalservice.model.dto.service;

import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.model.entity.MedicalServiceDep;
import org.springframework.stereotype.Component;

@Component
public class MedicalServiceConverter {

    public MedicalServiceShortDto entityToMedicalServiceShortDto(MedicalServiceDep medicalServiceDep) {
        MedicalService medicalService = medicalServiceDep.getMedicalService();
        return MedicalServiceShortDto.builder()
                .medicalServiceDepId(medicalServiceDep.getId())
                .serviceIdentifier(medicalService.getIdentifier())
                .serviceName(medicalService.getName())
                .build();
    }

}
