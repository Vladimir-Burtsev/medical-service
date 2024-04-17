package academy.kata.mis.medicalservice.model.dto.doctor;

import academy.kata.mis.medicalservice.model.dto.talon.TalonShortDto;

import java.util.List;

public record DoctorWithTalonsDto(DoctorShortDto doctor,
                                  List<TalonShortDto> talons) {
}
