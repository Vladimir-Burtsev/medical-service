package academy.kata.mis.medicalservice.model.dto.appeal;

import lombok.Builder;

import java.util.UUID;
@Builder
public record RequestSendAppealToReportService (UUID userId, String userEmail, String info, UUID operationId) {
}
