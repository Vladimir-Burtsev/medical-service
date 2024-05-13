package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Appeal;

import java.util.UUID;

public interface ReportServiceSender {
    void sendInReportService(UUID userId, boolean email, Appeal appeal, UUID operationId);
    String generateInfo(Appeal appeal);
}
