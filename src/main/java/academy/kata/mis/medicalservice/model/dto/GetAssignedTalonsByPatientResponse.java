package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;

import java.util.List;

public record GetAssignedTalonsByPatientResponse(List<TalonWithDoctorShortDto> talons) {
}
