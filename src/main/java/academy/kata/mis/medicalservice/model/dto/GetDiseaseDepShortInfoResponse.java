package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;

import java.util.List;

public record GetDiseaseDepShortInfoResponse(List<DiseaseShortInfoDto> diseases) {
}
