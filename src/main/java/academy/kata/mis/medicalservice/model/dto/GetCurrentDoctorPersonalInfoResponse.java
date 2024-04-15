package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;

public record GetCurrentDoctorPersonalInfoResponse(DoctorShortDto doctor,
                                                   OrganizationShortDto organization,
                                                   DepartmentShortDto department,
                                                   String cabinetNumber) {
}
