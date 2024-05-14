package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientService {
    List<Patient> findAllByUserId(UUID userId);
    Boolean existsPatientByUserIdAndOrganizationId(UUID userId, long organizationId);
    Patient getPatientById(long patientId);
    boolean isPatientExistsAndFromSameOrganizationAsDoctor(long patientId, long doctorId);
    Optional<String> getPatientUserIdByPatientId(long patientId);
}
