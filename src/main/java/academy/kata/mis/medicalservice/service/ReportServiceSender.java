package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.enums.CommandType;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ReportServiceSender {
    void sendInReportService(UUID userId, boolean email, Appeal appeal, UUID operationId);

    String generateInfo(Appeal appeal);

    void sendInMessageService(CommandType commandType,
                                     String email,
                                     String subject,
                                     LocalDateTime talonTime,
                                     String doctorFirstName,
                                     String doctorLastName,
                                     String departmentName,
                                     String organizationName);
}
