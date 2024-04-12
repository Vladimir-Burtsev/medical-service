package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentWithTalonsDto;

import java.util.List;

public record GetActivePatientTalonsByDepartmentsResponse(List<DepartmentWithTalonsDto> departments) {
}
