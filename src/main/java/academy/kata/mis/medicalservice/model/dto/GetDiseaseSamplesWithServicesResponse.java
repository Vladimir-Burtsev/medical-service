package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.sample.DiseaseSampleDto;
import lombok.Builder;

import java.util.List;

@Builder
public record GetDiseaseSamplesWithServicesResponse(List<DiseaseSampleDto> samples) {
}
