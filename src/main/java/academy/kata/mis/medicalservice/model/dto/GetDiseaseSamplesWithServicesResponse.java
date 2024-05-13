package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleDto;

import java.util.List;

public record GetDiseaseSamplesWithServicesResponse(List<DiseaseSampleDto> samples) {
}
