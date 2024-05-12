package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.model.enums.InsuranceType;

public interface AppealService {
    Appeal createPatientAppeal(DiseaseDep diseaseDep, Patient patient, InsuranceType insuranceType);

    Appeal save(Appeal appeal);
}
