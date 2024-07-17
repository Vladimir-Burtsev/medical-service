package academy.kata.mis.medicalservice.model.dto.sample;

import academy.kata.mis.medicalservice.model.dto.service.convertor.MedicalServiceConvertor;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import academy.kata.mis.medicalservice.model.entity.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DiseaseSampleConverter {

    private final MedicalServiceConvertor medicalServiceConvertor;

    public DiseaseSampleDto servicesDepIdToDiseaseSampleDto(Set<Long> medicalServiceDepIdSet, Map<Long, MedicalService> medicalServiceWithServiceDepIdMap) {
        List<MedicalServiceShortDto> medicalServiceShortDtoList = new ArrayList<>();
        for(Long medicalServiceDepId: medicalServiceDepIdSet) {
            medicalServiceShortDtoList.add(medicalServiceConvertor.serviceDepIdToMedicalServiceShortDto(medicalServiceDepId, medicalServiceWithServiceDepIdMap));
        }
        return new DiseaseSampleDto(medicalServiceShortDtoList);
    }

}
