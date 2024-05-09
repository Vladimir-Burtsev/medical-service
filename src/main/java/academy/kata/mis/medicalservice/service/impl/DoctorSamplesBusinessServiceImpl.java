package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleConverter;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleDto;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DoctorSamplesBusinessServiceImpl implements DoctorSamplesBusinessService {

    private final DiseaseSampleConverter diseaseSampleConverter;

    @Override
    public GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDep(DiseaseDep diseaseDep) {
        Set<DiseaseSample> diseaseSampleSet = diseaseDep.getDiseaseSamples();
        List<DiseaseSampleDto> diseaseSampleDtoList = new ArrayList<>();
        for (DiseaseSample diseaseSample : diseaseSampleSet) {
            diseaseSampleDtoList.add(diseaseSampleConverter.entityToDiseaseSampleDto(diseaseSample));
        }
        return GetDiseaseSamplesWithServicesResponse.builder()
                .samples(diseaseSampleDtoList)
                .build();
    }
}
