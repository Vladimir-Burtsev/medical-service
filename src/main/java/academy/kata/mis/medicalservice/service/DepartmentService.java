package academy.kata.mis.medicalservice.service;

public interface DepartmentService {

    long getDepartmentIdByDoctorId(long id);

    Long getDepartmentIdByTalonId(Long talonId);
}
