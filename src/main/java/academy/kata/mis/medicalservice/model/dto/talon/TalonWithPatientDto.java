package academy.kata.mis.medicalservice.model.dto.talon;

import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TalonWithPatientDto(long talonId, LocalDate date, LocalTime time, PatientShortDto patient) {
}
