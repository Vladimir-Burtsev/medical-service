package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientPersonalInformation;
import academy.kata.mis.medicalservice.model.dto.patient.PatientDto;

import java.util.List;
import java.util.UUID;

public interface PatientBusinessService {

    GetCurrentPatientPersonalInformation getPatientPersonalInformationByUser(UUID userId);

    List<PatientDto> findPatientInformationByUserId(UUID userId);
}
