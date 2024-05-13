package academy.kata.mis.medicalservice.service;

import java.util.UUID;

public interface ReportServiceSender {
    void sendInReportService(UUID userId, String email, String info, UUID operationId);
    void sendInMessageService(String userEmail, String subject, String text);
}
