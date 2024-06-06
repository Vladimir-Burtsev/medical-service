package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.talon.TalonDto;

import java.util.List;
import java.util.UUID;

public interface TalonBusinessService {
    void cancelReservationTalon(Long talonId);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
    GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId);
    List<TalonDto> getAllByTomorrow();
}
