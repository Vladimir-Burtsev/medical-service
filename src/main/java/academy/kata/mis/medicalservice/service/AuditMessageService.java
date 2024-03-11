package academy.kata.mis.medicalservice.service;

public interface AuditMessageService {
    void sendAudit(String initiator, String operation, String message);
}
