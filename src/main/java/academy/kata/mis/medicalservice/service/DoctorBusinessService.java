package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorBusinessService {
    void checkDoctorExistAndCurrent(long doctorId, UUID userId, long diseaseDepId);
}
