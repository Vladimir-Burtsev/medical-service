package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.talon.DoctorTalonsOnDayDto;

import java.util.List;

public record GetAllDoctorTalonsResponse(List<DoctorTalonsOnDayDto> days) {
}
