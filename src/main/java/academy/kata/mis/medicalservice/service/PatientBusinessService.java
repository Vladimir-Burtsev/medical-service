package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInfoResponse;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

public interface PatientBusinessService {
    GetCurrentPatientPersonalInfoResponse getPatientPersonalInformationByUser(UUID userId);

    boolean isPatientExistsAndFromSameOrganizationAsDoctor(long patientId, long doctorId);

    boolean isPatientExistAndAuthenticatedUserPatient(long patientId, String userId);

    UUID getUserId(long patientId);
}
