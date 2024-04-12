package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.model.enums.AppealStatus;
import academy.kata.mis.medicalservice.model.enums.InsuranceType;

import java.util.List;

public record GetPatientAppealFullInfoResponse(long appealId,
                                               AppealStatus appealStatus,//OPEN или CLOSED (если BLOCK то выводить CLOSED)
                                               String diseaseName,
                                               String diseaseCode,
                                               InsuranceType insuranceType,
                                               String money,
                                               List<VisitDto> visits) {
}
