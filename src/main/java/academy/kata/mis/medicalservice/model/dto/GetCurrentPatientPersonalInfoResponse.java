package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientPersonalInformation;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GetCurrentPatientPersonalInfoResponse(UUID userId,
                                                    PersonDto person,
                                                    List<PatientPersonalInformation> patients) {
}
