package academy.kata.mis.medicalservice.model;

import academy.kata.mis.medicalservice.model.dto.talon.TalonDto;

import java.util.List;

public record GetTalonsTomorrow(List<TalonDto> talons) {
}
