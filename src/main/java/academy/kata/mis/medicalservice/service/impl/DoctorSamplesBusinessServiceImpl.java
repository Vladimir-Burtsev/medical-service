package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleConverter;
import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleDto;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DiseaseDepService;
import academy.kata.mis.medicalservice.service.DoctorSamplesBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class DoctorSamplesBusinessServiceImpl implements DoctorSamplesBusinessService {

    private final DiseaseSampleConverter diseaseSampleConverter;
    private final DoctorService doctorService;
    private final DiseaseDepService diseaseDepService;

    @Override
    public GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDep(Doctor doctor, DiseaseDep diseaseDep, UUID authUserId) {
        equalsDoctorUserIdWithAuthUserId(doctor.getUserId(), authUserId);
        equalsDoctorDepWithDiseaseDep(doctor, diseaseDep);

        Set<DiseaseSample> diseaseSampleSet = diseaseDep.getDiseaseSamples();
        List<DiseaseSampleDto> diseaseSampleDtoList = new ArrayList<>();
        for (DiseaseSample diseaseSample : diseaseSampleSet) {
            diseaseSampleDtoList.add(diseaseSampleConverter.entityToDiseaseSampleDto(diseaseSample));
        }
        return GetDiseaseSamplesWithServicesResponse.builder()
                .samples(diseaseSampleDtoList)
                .build();

    }

    private void equalsDoctorUserIdWithAuthUserId(UUID doctorUserId, UUID authUserId) {
        if(!doctorUserId.equals(authUserId)) {
            throw new LogicException("Недостаточно прав");
        }
    }

    private void equalsDoctorDepWithDiseaseDep(Doctor doctor, DiseaseDep diseaseDep) {
        if(!doctor.getDepartment().equals(diseaseDep.getDepartment())) {
            throw new LogicException("Неверные данные");
        }
    }

}
