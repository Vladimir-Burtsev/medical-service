package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleConverter;
import academy.kata.mis.medicalservice.service.DiseaseSampleService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorSamplesBusinessServiceImpl implements DoctorSamplesBusinessService {
    private final DiseaseSampleService diseaseSampleService;
    private final DiseaseSampleConverter diseaseSampleConverter;

    @Override
    public GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDepAndDoctor(long doctorId,
                                                                                                    long diseaseDepId) {
        return new GetDiseaseSamplesWithServicesResponse(
                diseaseSampleService.getByDoctorAndDiseaseDep(doctorId, diseaseDepId).stream()
                        .map(diseaseSampleConverter::entityToDiseaseSampleDto)
                        .collect(Collectors.toList()));
    }
}
