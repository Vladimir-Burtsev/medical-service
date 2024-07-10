package academy.kata.mis.medicalservice.model.dto.talon;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientAndPersonIdDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TalonDto (long talonId,
                        LocalDateTime localDateTime,
                        PatientAndPersonIdDto patient,
                        DoctorDto doctor) {
}
