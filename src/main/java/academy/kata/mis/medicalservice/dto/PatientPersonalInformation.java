package academy.kata.mis.medicalservice.dto;

import academy.kata.mis.medicalservice.dto.feign.OrganizationDto;
import lombok.Builder;

@Builder
public record PatientPersonalInformation(long patientId,
                                         OrganizationDto organization) {}
