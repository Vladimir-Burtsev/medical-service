package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.talon.CancelTalonDto;
import academy.kata.mis.medicalservice.model.entity.Talon;

import java.util.UUID;

public interface TalonBusinessService {
    CancelTalonDto cancelReservationTalon(Long talonId, UUID userId);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
    GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId);
}
