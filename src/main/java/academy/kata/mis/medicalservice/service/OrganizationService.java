package academy.kata.mis.medicalservice.service;

public interface OrganizationService {

    Long getOrganizationIdByDepartmentId(Long departmentId);

    long getOrganizationIdByDoctorId(long id);
}
