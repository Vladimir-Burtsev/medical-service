package academy.kata.mis.medicalservice.model.dto.talon;

import java.time.LocalDate;
import java.time.LocalTime;

public record TalonShortDto(long talonId, LocalDate date, LocalTime time, boolean isPatientAssigned) {
}
