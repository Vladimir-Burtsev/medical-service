package academy.kata.mis.medicalservice.model.dto.positions;

import lombok.Builder;

@Builder
public record PositionsNameAndCabinetDto(long id,
                                         String name,
                                         String cabinet) {
}
