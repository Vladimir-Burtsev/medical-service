package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorService {
    Doctor findByUserId(UUID id);
    Long getDoctorIdByTalonId(Long talonId);
    Long getDoctorPersonIdByTalonId(Long talonId);
}
