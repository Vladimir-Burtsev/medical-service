package academy.kata.mis.medicalservice.model.dto.doctor;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;

public record DoctorShortInfoInOrganizationDto(long doctorId,
                                               String positionName,
                                               OrganizationShortDto organization,
                                               DepartmentShortDto department) {
}
