package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;

public interface VisitService {
    void createPatientVisit(Doctor doctor, Appeal appeal);
}
