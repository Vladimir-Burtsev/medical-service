package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.GetFullTalonInformationResponse;
import academy.kata.mis.medicalservice.model.dto.talon.CancelTalonDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonDto;

import java.util.List;
import java.util.UUID;

public interface TalonBusinessService {
    CancelTalonDto cancelReservationTalon(Long talonId, UUID userId);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
    GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId);
    boolean existsTalonById(Long talonId);
    boolean isCurrentAuthDoctorAssignToTalonByUserIdAndTalonId(UUID userId, Long talonId);
    GetFullTalonInformationResponse getFullTalonInfoById(Long talonId);
    List<TalonDto> getAllByTomorrow();
}
