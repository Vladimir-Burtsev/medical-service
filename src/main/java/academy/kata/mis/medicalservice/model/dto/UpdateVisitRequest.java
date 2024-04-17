package academy.kata.mis.medicalservice.model.dto;

import java.util.List;

public record UpdateVisitRequest(long doctorId,
                                 long visitId,
                                 List<Long> medicalServicesDepIds,
                                 boolean isClosed) {
}
