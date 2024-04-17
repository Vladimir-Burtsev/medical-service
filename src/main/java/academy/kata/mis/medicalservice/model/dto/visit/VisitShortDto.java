package academy.kata.mis.medicalservice.model.dto.visit;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;

import java.time.LocalDateTime;

public record VisitShortDto(long visitId,
                            LocalDateTime visitTime,
                            DoctorShortDto doctor) {
}
