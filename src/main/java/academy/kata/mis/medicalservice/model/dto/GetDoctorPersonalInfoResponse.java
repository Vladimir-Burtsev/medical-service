package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortInfoInOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;

import java.util.List;

public record GetDoctorPersonalInfoResponse(PersonDto person,
                                            List<DoctorShortInfoInOrganizationDto> doctors) {
}
