package academy.kata.mis.medicalservice.model.dto;

import java.util.List;

public record GetAllDoctorTalonsResponse(List<DoctorTalonsOnDayDto> days) {
}
