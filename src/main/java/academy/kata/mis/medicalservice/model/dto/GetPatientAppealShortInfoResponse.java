package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.appeal.AppealShortInfoDto;

import java.util.List;

public record GetPatientAppealShortInfoResponse(List<AppealShortInfoDto> appeals) {
}
