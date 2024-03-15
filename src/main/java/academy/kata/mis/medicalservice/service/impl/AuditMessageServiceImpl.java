package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.kafka.audit.AuditEventMessage;
import academy.kata.mis.medicalservice.model.dto.kafka.audit.SaveAuditEvent;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import academy.kata.mis.medicalservice.service.AuditMessageService;
import academy.kata.mis.medicalservice.service.KafkaSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditMessageServiceImpl implements AuditMessageService {
    private final KafkaSenderService kafkaSenderService;

    @Value("${spring.kafka.producer.topic.save-audit}")
    private String topic;

    @Value("${spring.application.name}")
    private String systemName;

    @Override
    public void sendAudit(String initiator, String operation, String message) {
        kafkaSenderService.sendToKafkaAsync(
                topic,
                new SaveAuditEvent(
                        CommandType.SAVE_AUDIT_EVENT,
                        new AuditEventMessage(systemName, initiator, operation, LocalDateTime.now(), message)
                )
        );
    }
}
