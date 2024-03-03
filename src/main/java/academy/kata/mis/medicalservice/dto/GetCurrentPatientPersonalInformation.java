package academy.kata.mis.medicalservice.dto;

import academy.kata.mis.medicalservice.dto.feign.PersonDto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GetCurrentPatientPersonalInformation(UUID userId,
                                                   PersonDto person,
                                                   List<PatientPersonalInformation> patients) {
}
