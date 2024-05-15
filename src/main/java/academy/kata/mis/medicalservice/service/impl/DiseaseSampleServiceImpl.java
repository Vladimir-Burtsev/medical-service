package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.DiseaseSample;
import academy.kata.mis.medicalservice.repository.DiseaseSampleRepository;
import academy.kata.mis.medicalservice.service.DiseaseSampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiseaseSampleServiceImpl implements DiseaseSampleService {

    private final DiseaseSampleRepository diseaseSampleRepository;

    @Override
    public Set<DiseaseSample> getByDoctorAndDiseaseDep(long doctorId, long diseaseDepId) {
        return diseaseSampleRepository.getDiseaseSampleByDoctorIdAndAndDiseaseDepId(doctorId, diseaseDepId);
    }
}
