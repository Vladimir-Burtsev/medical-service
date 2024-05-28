package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleConverter;
import academy.kata.mis.medicalservice.service.DiseaseSampleService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class DoctorSamplesBusinessServiceImpl implements DoctorSamplesBusinessService {
    private final DiseaseSampleService diseaseSampleService;
    private final DiseaseSampleConverter diseaseSampleConverter;

    @Override
    public GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDepAndDoctor(long doctorId,
                                                                                                    long diseaseDepId) {
        diseaseSampleConverter.loadMedicalServiceWithServiceDepIdMap(diseaseSampleService
                .getServiceDepIdByDoctorIdAndDiseaseDepId(doctorId, diseaseDepId));
        return new GetDiseaseSamplesWithServicesResponse(diseaseSampleService
                .getDiseaseSamplesIdByDoctorIdAndDiseaseId(doctorId, diseaseDepId).stream()
                .map(diseaseSampleConverter::diseaseSamplesIdToDiseaseSampleDto).toList());

    }
}
