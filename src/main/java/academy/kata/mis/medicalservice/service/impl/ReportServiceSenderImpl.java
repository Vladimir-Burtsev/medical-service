package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.appeal.AppealToMessageService;
import academy.kata.mis.medicalservice.model.dto.appeal.RequestSendAppealToReportService;
import academy.kata.mis.medicalservice.service.KafkaSenderService;
import academy.kata.mis.medicalservice.service.ReportServiceSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceSenderImpl implements ReportServiceSender {
    private final KafkaSenderService kafkaSenderService;

    @Value("${spring.kafka.producer.topic.report-message}")
    private String topic;
    @Value("${spring.kafka.producer.topic.send-message}")
    private String topicMessageService;

    @Override
    public void sendInReportService(UUID userId, String userEmail, String info, UUID operationId) {
        kafkaSenderService.sendToKafkaAsync(topic,
                RequestSendAppealToReportService.builder()
                        .userId(userId)
                        .userEmail(userEmail)
                        .info(info)
                        .operationId(operationId)
                        .build()
        );
    }

    @Override
    public void sendInMessageService(String type, String userEmail, String subject, String text) {
        kafkaSenderService.sendToKafkaAsync(topicMessageService,
                AppealToMessageService.builder()
                        .type(type)
                        .userEmail(userEmail)
                        .subject(subject)
                        .text(text)
                        .build()
        );
    }
}
