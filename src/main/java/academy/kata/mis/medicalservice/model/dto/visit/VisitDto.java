package academy.kata.mis.medicalservice.model.dto.visit;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;

import java.time.LocalDateTime;
import java.util.List;

public record VisitDto(long visitId,
                       LocalDateTime visitTime,
                       DoctorShortDto doctor,
                       List<MedicalServiceShortDto> medicalServices) {
}
