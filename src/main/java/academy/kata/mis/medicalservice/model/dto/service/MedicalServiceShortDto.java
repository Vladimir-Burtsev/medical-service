package academy.kata.mis.medicalservice.model.dto.service;

import lombok.Builder;

@Builder
public record MedicalServiceShortDto(long medicalServiceDepId,
                                     String serviceIdentifier,
                                     String serviceName) {
}
