package academy.kata.mis.medicalservice.model.dto.service;

import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.service.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicalServiceConverter {

    private final MedicalServiceService medicalServiceService;

    private Map<Long, MedicalService> medicalServiceWithServiceDepIdMap;

    public MedicalServiceShortDto serviceDepIdToMedicalServiceShortDto(Long medicalServiceDepId) {
        return MedicalServiceShortDto.builder()
                .medicalServiceDepId(medicalServiceDepId)
                .serviceName(medicalServiceWithServiceDepIdMap.get(medicalServiceDepId).getName())
                .serviceIdentifier(medicalServiceWithServiceDepIdMap.get(medicalServiceDepId).getIdentifier()).build();
    }

    public void loadMedicalServiceWithServiceDepIdMap(Set<Long> servicesDepIdJoinWithDiseaseSample) {
        Set<MedicalService> medicalServiceSet = medicalServiceService
                .getMedicalServiceByServicesDepId(servicesDepIdJoinWithDiseaseSample);
        medicalServiceWithServiceDepIdMap = medicalServiceSet.stream()
                .collect(Collectors.toMap(
                        medicalService -> medicalService.getServicesDep().iterator().next().getId(),
                        medicalService -> medicalService
                ));
    }

}
