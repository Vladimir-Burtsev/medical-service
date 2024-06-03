package academy.kata.mis.medicalservice.model.dto.service;

import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleDto;
import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.service.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicalServiceConverter {

    public MedicalServiceShortDto serviceDepIdToMedicalServiceShortDto(Long medicalServiceDepId, Map<Long, MedicalService> medicalServiceWithServiceDepIdMap) {
        return MedicalServiceShortDto.builder()
                .medicalServiceDepId(medicalServiceDepId)
                .serviceName(medicalServiceWithServiceDepIdMap.get(medicalServiceDepId).getName())
                .serviceIdentifier(medicalServiceWithServiceDepIdMap.get(medicalServiceDepId).getIdentifier()).build();
    }
}
