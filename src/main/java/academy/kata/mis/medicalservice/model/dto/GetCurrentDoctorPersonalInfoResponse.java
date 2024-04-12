package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortInfoInOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;

import java.util.List;
import java.util.UUID;

public record GetCurrentDoctorPersonalInfoResponse(UUID userId,
                                                   PersonDto person,
                                                   List<DoctorShortInfoInOrganizationDto> doctors) {
}
