package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.talon.TalonShortDto;

import java.time.LocalDate;
import java.util.List;

public record DoctorTalonsOnDayDto(LocalDate day, List<TalonShortDto> talons) {
}
