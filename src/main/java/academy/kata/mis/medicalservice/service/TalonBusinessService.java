package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.entity.Talon;

import java.util.UUID;

public interface TalonBusinessService {
    Talon cancelReservationTalon(Long talonId);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
    String getResponseTalonCancel(Long talonId);
    GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId);

    Long getDoctorPersonIdByTalonId(Long talonId);

    Long getDoctorIdByTalonId(Long talonId);
}
