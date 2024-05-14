package academy.kata.mis.medicalservice.model.dto.disease;

import lombok.Builder;

@Builder
public record DiseaseShortInfoDto(long diseaseDepId,
                                  String diseaseName,
                                  String diseaseIdentifier) {
}
