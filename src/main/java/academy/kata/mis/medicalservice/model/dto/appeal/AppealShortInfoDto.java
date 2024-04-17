package academy.kata.mis.medicalservice.model.dto.appeal;

import java.time.LocalDate;

public record AppealShortInfoDto(long appealId,
                                 String diseaseName,
                                 String diseaseCode,
                                 LocalDate firstVisit) {
}
