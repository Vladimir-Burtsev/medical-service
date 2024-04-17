package academy.kata.mis.medicalservice.model.dto.service;

public record MedicalServiceShortDto(long medicalServiceDepId,
                                     String serviceIdentifier,
                                     String serviceName) {
}
