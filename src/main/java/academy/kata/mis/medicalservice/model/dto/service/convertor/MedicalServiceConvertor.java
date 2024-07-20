package academy.kata.mis.medicalservice.model.dto.service.convertor;

import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.model.entity.MedicalServiceDep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicalServiceConvertor {

    public MedicalServiceShortDto serviceDepIdToMedicalServiceShortDto(Long medicalServiceDepId, Map<Long, MedicalService> medicalServiceWithServiceDepIdMap) {
        return MedicalServiceShortDto.builder()
                .medicalServiceDepId(medicalServiceDepId)
                .serviceName(medicalServiceWithServiceDepIdMap.get(medicalServiceDepId).getName())
                .serviceIdentifier(medicalServiceWithServiceDepIdMap.get(medicalServiceDepId).getIdentifier()).build();
    }

    private MedicalServiceShortDto convertMedicalServiceShortDtoToList (MedicalServiceDep medicalServiceDep) {
        return MedicalServiceShortDto.builder()
                .medicalServiceDepId(medicalServiceDep.getId())
                .serviceIdentifier(medicalServiceDep.getMedicalService().getIdentifier())
                .serviceName(medicalServiceDep.getMedicalService().getName())
                .build();
    }
    public List<MedicalServiceShortDto> convertMedicalServiceToMedicalServiceShortDto (List<MedicalServiceDep> medicalService) {
        return medicalService.stream()
                .map(this::convertMedicalServiceShortDtoToList)
                .collect(Collectors.toList());
    }
}
