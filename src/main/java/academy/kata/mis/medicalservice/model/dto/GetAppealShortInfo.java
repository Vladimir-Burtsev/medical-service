package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.disease.DiseaseShortInfoDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;
import academy.kata.mis.medicalservice.model.dto.visit.VisitShortDto;
import academy.kata.mis.medicalservice.model.enums.AppealStatus;
import lombok.Builder;

import java.util.List;
@Builder
public record GetAppealShortInfo(long appealId,
                                 AppealStatus appealStatus,
                                 PatientShortDto patient,
                                 DiseaseShortInfoDto disease,
                                 List<VisitShortDto> visits) {
}
