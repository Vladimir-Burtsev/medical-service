package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import lombok.Builder;

@Builder
public record PatientPersonalInformation(long patientId,
                                         OrganizationDto organization) {}
