package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorService {
    boolean isExistsByUserIdAndId(UUID doctorUUID, long id);
    Doctor existsByUserIdAndId(UUID doctorUUID, long id);
    Long getDoctorIdByTalonId(Long talonId);
    Long getDoctorPersonIdByTalonId(Long talonId);
}
