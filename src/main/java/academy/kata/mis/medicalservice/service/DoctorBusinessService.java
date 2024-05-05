package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Department;

public interface DoctorBusinessService {
    void isDiseaseDepExistsAndMatchesDoctorDepartment(Long diseaseDepId, Department department);
}
