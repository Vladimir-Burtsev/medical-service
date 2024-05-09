package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.entity.Doctor;

public interface AppealBusinessService {
    GetAppealShortInfo createPatientVisit(Doctor doctor, long diseaseDepId, long patientId);
}
