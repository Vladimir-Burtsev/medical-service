package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.employee.EmployeeShortInfoInOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import lombok.Builder;


import java.util.List;

@Builder


public record GetDoctorPersonalInfoResponse(PersonDto person,
                                            List<EmployeeShortInfoInOrganizationDto> doctors) {
}
