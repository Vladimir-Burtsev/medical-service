package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.dto.GetCurrentPatientPersonalInformation;

import java.util.UUID;

public interface PatientBusinessService {

    GetCurrentPatientPersonalInformation getPatientPersonalInformationByUser(UUID userId);
}
