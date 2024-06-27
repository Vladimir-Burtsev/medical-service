package academy.kata.mis.medicalservice.model.dto.employee;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import lombok.Builder;



@Builder
public record EmployeeShortInfoInOrganizationDto(long employeeId,
                                                 String positionName,
                                                 OrganizationShortDto organization,
                                                 DepartmentShortDto department) {
}
