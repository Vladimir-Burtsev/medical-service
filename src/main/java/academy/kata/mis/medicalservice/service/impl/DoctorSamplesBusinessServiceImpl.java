package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleConverter;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleDto;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceConverter;
import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import academy.kata.mis.medicalservice.model.entity.MedicalService;
import academy.kata.mis.medicalservice.service.DiseaseSampleService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import academy.kata.mis.medicalservice.service.MedicalServiceService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class DoctorSamplesBusinessServiceImpl implements DoctorSamplesBusinessService {
    private final DiseaseSampleService diseaseSampleService;
    private final DiseaseSampleConverter diseaseSampleConverter;
    private final MedicalServiceConverter medicalServiceConverter;
    private final MedicalServiceService medicalServiceService;

    @Override
    public GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDepAndDoctor(long doctorId,
                                                                                                    long diseaseDepId) {
        Map<Long, MedicalService> medicalServiceWithServiceDepIdMap;

        Set<MedicalService> medicalServiceSet = medicalServiceService
                .getMedicalServiceByServicesDepId(diseaseSampleService
                        .getServiceDepIdByDoctorIdAndDiseaseDepId(doctorId, diseaseDepId));
        medicalServiceWithServiceDepIdMap = medicalServiceSet.stream()
                .collect(Collectors.toMap(
                        medicalService -> medicalService.getServicesDep().iterator().next().getId(),
                        medicalService -> medicalService
                ));

        List<DiseaseSampleDto> diseaseSampleDtoList = new ArrayList<>();

        for (Long diseaseSampleId: diseaseSampleService.getDiseaseSamplesIdByDoctorIdAndDiseaseId(doctorId, diseaseDepId)) {
            diseaseSampleDtoList.add(diseaseSampleConverter.servicesDepIdToDiseaseSampleDto(diseaseSampleService
                    .getServiceDepIdByDiseaseSampleId(diseaseSampleId), medicalServiceWithServiceDepIdMap));
        }

        return new GetDiseaseSamplesWithServicesResponse(diseaseSampleDtoList);
    }
}
