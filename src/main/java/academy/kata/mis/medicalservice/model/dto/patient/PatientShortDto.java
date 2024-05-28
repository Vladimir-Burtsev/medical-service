package academy.kata.mis.medicalservice.model.dto.patient;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientShortDto(long patientId,
                              String patientFirstName,
                              String patientLastname,
                              String patientPatronymic,
                              LocalDate birthday) {
}
