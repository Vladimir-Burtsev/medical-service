package academy.kata.mis.medicalservice.model.dto.person;

import lombok.Builder;

@Builder
public record PersonShortDto(long id,
                             String firstName,
                             String lastName) {
}

