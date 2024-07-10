package academy.kata.mis.medicalservice.model.dto.feign;

import lombok.Builder;

@Builder
public record PersonDto(long id, String firstName, String lastName) {
}