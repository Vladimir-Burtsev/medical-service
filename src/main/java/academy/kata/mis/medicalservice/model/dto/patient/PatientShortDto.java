package academy.kata.mis.medicalservice.model.dto.patient;

import java.time.LocalDate;

public record PatientShortDto(long patientId,
                              String patientFirstName,
                              String patientLastname,
                              String patientPatronymic,
                              LocalDate birthday) {
}
