package academy.kata.mis.medicalservice.model.dto.sample;

import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import lombok.Builder;

import java.util.List;


public record DiseaseSampleDto(List<MedicalServiceShortDto> medicalServices) {
}
