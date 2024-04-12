package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;

import java.util.List;

public record GetActiveTalonsByPatientResponse(List<TalonWithDoctorShortDto> talons) {
}