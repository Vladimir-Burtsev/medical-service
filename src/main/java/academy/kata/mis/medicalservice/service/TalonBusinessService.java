package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import java.util.UUID;

public interface TalonBusinessService {
    void cancelReservationTalon(Long talonId);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
    GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId);
}
