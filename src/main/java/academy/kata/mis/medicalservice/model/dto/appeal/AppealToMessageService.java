package academy.kata.mis.medicalservice.model.dto.appeal;

import lombok.Builder;

@Builder
public record AppealToMessageService(String userEmail, String subject, String text) {
}
