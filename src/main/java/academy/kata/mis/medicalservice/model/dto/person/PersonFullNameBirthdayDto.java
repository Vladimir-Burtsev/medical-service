package academy.kata.mis.medicalservice.model.dto.person;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonFullNameBirthdayDto(long personId,
                                        String firstName,
                                        String lastName,
                                        String patronymic,
                                        LocalDate birthday) {
}
