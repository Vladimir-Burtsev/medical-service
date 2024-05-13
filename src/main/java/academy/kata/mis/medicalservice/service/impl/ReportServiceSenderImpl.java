package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.appeal.AppealToMessageService;
import academy.kata.mis.medicalservice.model.dto.appeal.AppealToMessageService;
import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.appeal.AppealToMessageService;
import academy.kata.mis.medicalservice.model.dto.appeal.RequestSendAppealToReportService;
import academy.kata.mis.medicalservice.model.dto.kafka.message_service.EventMessage;
import academy.kata.mis.medicalservice.model.dto.kafka.message_service.EventMessageParamsCancelTalon;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import academy.kata.mis.medicalservice.service.KafkaSenderService;
import academy.kata.mis.medicalservice.service.ReportServiceSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceSenderImpl implements ReportServiceSender {

    private final KafkaSenderService kafkaSenderService;
    private final PersonFeignClient personFeignClient;

    @Value("${spring.kafka.producer.topic.report-message}")
    private String topic;

    @Override
    public void sendInReportService(UUID userId, boolean isEmail, Appeal appeal, UUID operationId) {
        String email = checkExistEmail(isEmail, userId);
        String info = generateInfo(appeal);
        kafkaSenderService.sendToKafkaAsync(topic,
                RequestSendAppealToReportService.builder()
                        .userId(userId)
                        .userEmail(email)
                        .info(info)
                        .operationId(operationId)
                        .build()
        );
    }

    private String checkExistEmail(boolean email, UUID userId) {
        return email ? personFeignClient.getPersonEmailByUserId(userId) : null;
    }

    //todo убрать заглушки
    public String generateInfo(Appeal appeal) {
        return String.format("""
                        Диагноз: %s
                        Статус обращения: %s
                        Способ оплаты: %s
                        Сумма лечения: %s
                                                       
                        Дата открытия: %s
                        Врач: %s
                        Услуги: %s
                                                       
                        Дата закрытия: %s
                        Врач: %s""",
                appeal.getDiseaseDep().getDisease().getName(),
                appeal.getStatus(),
                appeal.getInsuranceType(),
                "1000",
                appeal.getOpenDate(),
                "doctorName",
                "appealServices",
                appeal.getClosedDate(),
                "doctorName"
        );
    }
}