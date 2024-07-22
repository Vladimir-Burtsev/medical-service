package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;

public interface VisitService {
    Visit createPatientVisit(Doctor doctor, Appeal appeal);
    Visit save(Visit visit);
    Visit findVisitById(long id);
    boolean existsVisitById (long visitId);
}
