package academy.kata.mis.medicalservice.service;

import java.util.UUID;

public interface DoctorBusinessService {
    boolean checkDoctorExistAndCurrent(long doctorId, UUID userId, long diseaseDepId);
}
