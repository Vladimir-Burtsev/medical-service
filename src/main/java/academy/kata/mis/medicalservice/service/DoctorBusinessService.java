package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorBusinessService {
    Doctor getDoctorIfExists(UUID doctorUUID, long id);
}
