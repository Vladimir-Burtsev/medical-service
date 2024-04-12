package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInfoResponse;

import java.util.UUID;

public interface PatientBusinessService {

    GetCurrentPatientPersonalInfoResponse getPatientPersonalInformationByUser(UUID userId);
}
