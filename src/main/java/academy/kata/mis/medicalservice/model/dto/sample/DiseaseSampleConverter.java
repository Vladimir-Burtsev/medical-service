package academy.kata.mis.medicalservice.model.dto.sample;

import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceConverter;
import academy.kata.mis.medicalservice.service.DiseaseSampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DiseaseSampleConverter {

    private final DiseaseSampleService diseaseSampleService;
    private final MedicalServiceConverter medicalServiceConverter;

    public DiseaseSampleDto diseaseSamplesIdToDiseaseSampleDto(Long diseaseSamplesId) {
        return new DiseaseSampleDto(diseaseSampleService.getServiceDepIdByDiseaseSampleId(diseaseSamplesId).stream()
                .map(medicalServiceConverter::serviceDepIdToMedicalServiceShortDto).toList());
    }

    public void loadMedicalServiceWithServiceDepIdMap(Set<Long> servicesDepIdJoinWithDiseaseSample) {
        medicalServiceConverter.loadMedicalServiceWithServiceDepIdMap(servicesDepIdJoinWithDiseaseSample);
    }

}
