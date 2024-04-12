package academy.kata.mis.medicalservice.model.dto.appeal;

import java.time.LocalDate;

public record AppealShortDto(long appealId,
                             LocalDate openDate,
                             LocalDate closedDate,
                             String diseaseName) {
}
