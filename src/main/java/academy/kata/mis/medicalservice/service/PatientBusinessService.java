package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInformation;

import java.util.UUID;

public interface PatientBusinessService {

    GetCurrentPatientPersonalInformation getPatientPersonalInformationByUser(UUID userId);
}
