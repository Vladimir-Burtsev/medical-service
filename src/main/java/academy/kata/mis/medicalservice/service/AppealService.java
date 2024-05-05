package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Appeal;

public interface AppealService {
    Appeal createPatientAppeal(Long diseaseDepId, Long patientId);
}
