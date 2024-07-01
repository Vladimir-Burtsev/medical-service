package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    boolean isExistsByUserIdAndId(UUID doctorUUID, long id);

    Doctor existsByUserIdAndId(UUID doctorUUID, long id);

    Long getDoctorIdByTalonId(Long talonId);

    Long getDoctorPersonIdByTalonId(Long talonId);

    List<Doctor> findAllByUserId(UUID userId);

    boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId);

    List<Doctor> findAllWithDepartmentsAndOrganizations(UUID userId);

    boolean isDoctorExistsById(Long id);

    long getPositionIdByDoctorId(long id);

    Long getDepartmentIdByDoctorId(Long doctorId);

    Long getPositionIdByDoctorId(Long doctorId);
}
