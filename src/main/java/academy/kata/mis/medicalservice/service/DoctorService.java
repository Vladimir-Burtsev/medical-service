package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorService {
    Doctor getDoctorIfExistsByUserIdAndId(UUID doctorUUID, long id);

    boolean isExistsByUserIdAndId(UUID doctorUUID, long id);
}
