package academy.kata.mis.medicalservice.model.dto.sample;

import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceConverter;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import academy.kata.mis.medicalservice.model.entity.MedicalServiceDep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DiseaseSampleConverter {

    private final MedicalServiceConverter medicalServiceConverter;

    public DiseaseSampleDto entityToDiseaseSampleDto(DiseaseSample diseaseSample) {
        Set<MedicalServiceDep> medicalServiceDepSet = diseaseSample.getServicesDep();
        List<MedicalServiceShortDto> medicalServiceShortDtoList = new ArrayList<>();
        for (MedicalServiceDep medicalServiceDep: medicalServiceDepSet) {
            medicalServiceShortDtoList.add(medicalServiceConverter.entityToMedicalServiceShortDto(medicalServiceDep));
        }
        return DiseaseSampleDto.builder()
                .medicalServices(medicalServiceShortDtoList)
                .build();
    }
}
