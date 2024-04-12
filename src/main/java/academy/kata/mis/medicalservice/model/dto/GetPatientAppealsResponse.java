package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.appeal.AppealShortDto;

import java.util.List;

public record GetPatientAppealsResponse(List<AppealShortDto> appeals) {
}
