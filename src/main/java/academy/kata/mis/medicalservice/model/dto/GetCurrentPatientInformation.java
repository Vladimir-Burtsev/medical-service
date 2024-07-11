package academy.kata.mis.medicalservice.model.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record GetCurrentPatientInformation(long personId,
                                           String firstName,
                                           String lastName,
                                           String patronymic,
                                           LocalDate birthday) {
}

