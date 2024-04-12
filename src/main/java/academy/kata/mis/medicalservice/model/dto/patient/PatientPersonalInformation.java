package academy.kata.mis.medicalservice.model.dto.patient;

import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import lombok.Builder;

@Builder
public record PatientPersonalInformation(long patientId,
                                         OrganizationDto organization) {}
