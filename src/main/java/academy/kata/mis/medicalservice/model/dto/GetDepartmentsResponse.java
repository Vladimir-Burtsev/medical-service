package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;

import java.util.List;

public record GetDepartmentsResponse(List<DepartmentShortDto> departments) {
}
