package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    Doctor existsByUserIdAndId(UUID doctorUUID, long id);
    Long getDoctorIdByTalonId(Long talonId);
    Long getDoctorPersonIdByTalonId(Long talonId);
    List<Doctor> findAllByUserId(UUID userId);
    Long getOrganizationIdByDepartmentId(long departmentId);
}
