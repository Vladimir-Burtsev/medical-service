package academy.kata.mis.medicalservice.service;

import java.util.UUID;

public interface DoctorService {

    boolean isExistByIdAndUserId(long doctorId, UUID userId, long diseaseDepId);
}
