package academy.kata.mis.medicalservice.model.dto;

import lombok.Builder;

@Builder
public record PositionDto(long id,String name) {
}
