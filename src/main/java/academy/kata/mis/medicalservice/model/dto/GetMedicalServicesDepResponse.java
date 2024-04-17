package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;

import java.util.List;

public record GetMedicalServicesDepResponse(List<MedicalServiceShortDto> medicalServices) {
}
