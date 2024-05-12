package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetAppealShortInfo;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.enums.InsuranceType;

public interface AppealBusinessService {
    GetAppealShortInfo createPatientVisit(Doctor doctor,
                                          long diseaseDepId,
                                          long patientId,
                                          InsuranceType insuranceType);
}
