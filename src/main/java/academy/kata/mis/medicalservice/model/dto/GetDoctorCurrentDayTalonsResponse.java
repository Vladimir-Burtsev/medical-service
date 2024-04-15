package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithPatientDto;

import java.time.LocalDate;
import java.util.List;

public record GetDoctorCurrentDayTalonsResponse(LocalDate today, List<TalonWithPatientDto> talons) {
}
