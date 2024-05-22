package academy.kata.mis.medicalservice.model.dto.person;

import lombok.Builder;

@Builder
public record PersonFullNameDto(long id,
                                String firstName,
                                String lastName,
                                String patronymic) {
}
