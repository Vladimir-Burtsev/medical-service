package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

public interface AppealBusinessService {
    void createPatientVisit(Doctor doctor, long diseaseDepId, long patientId);
}
