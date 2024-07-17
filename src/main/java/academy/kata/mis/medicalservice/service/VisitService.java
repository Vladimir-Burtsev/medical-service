package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;

import java.util.UUID;

public interface VisitService {
    Visit createPatientVisit(Doctor doctor, Appeal appeal);
    Visit save(Visit visit);
    Visit findVisitById(long id);
    VisitDto getVisitInfo(long visitId);
    boolean validateGetVisitInfo(long visitId, UUID doctorId);
}
