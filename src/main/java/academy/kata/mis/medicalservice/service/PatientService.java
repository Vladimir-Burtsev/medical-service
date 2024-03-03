package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.Patient;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    List<Patient> findAllByUserId(UUID userId);
}
