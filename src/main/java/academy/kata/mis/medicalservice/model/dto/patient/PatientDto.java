package academy.kata.mis.medicalservice.model.dto.patient;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PatientDto(long id, long personId, UUID userId, long organizationId) {
}
