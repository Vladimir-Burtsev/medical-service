package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.DiseaseSample;

import java.util.Set;

public interface DiseaseSampleService {

    Set<DiseaseSample> getByDoctorAndDiseaseDep(long doctorId, long diseaseDepId);

}
