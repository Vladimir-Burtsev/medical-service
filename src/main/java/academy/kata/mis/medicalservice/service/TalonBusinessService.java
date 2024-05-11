package academy.kata.mis.medicalservice.service;

import java.util.UUID;

public interface TalonBusinessService {
    void cancelReservationTalon(Long talonId);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
}
