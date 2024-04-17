package academy.kata.mis.medicalservice.model.dto.employee;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;

public record EmployeeShortInfoInOrganizationDto(long employeeId,
                                                 String positionName,
                                                 OrganizationShortDto organization,
                                                 DepartmentShortDto department) {
}
