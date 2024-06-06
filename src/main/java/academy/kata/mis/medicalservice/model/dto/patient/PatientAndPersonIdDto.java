package academy.kata.mis.medicalservice.model.dto.patient;

import lombok.Builder;

@Builder
public record PatientAndPersonIdDto(long id,
                                    long personId) {
}
