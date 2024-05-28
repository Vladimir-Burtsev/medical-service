package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.kafka.message_service.EventMessage;
import academy.kata.mis.medicalservice.model.dto.kafka.message_service.EventMessageParamsCancelTalon;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import academy.kata.mis.medicalservice.service.KafkaSenderService;
import academy.kata.mis.medicalservice.service.MessageServiceSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class MessageServiceSenderImpl implements MessageServiceSender {
    private final KafkaSenderService kafkaSenderService;
    private final PersonFeignClient personFeignClient;

    @Value("${spring.kafka.producer.topic.send-message}")
    private String topicMessageService;

    @Override
    public void sendInMessageService(CommandType commandType,
                                     String email,
                                     String subject,
                                     LocalDateTime talonTime,
                                     String doctorFirstName,
                                     String doctorLastName,
                                     String departmentName,
                                     String organizationName) {
        kafkaSenderService.sendToKafkaAsync(
                topicMessageService,
                new EventMessage(
                        commandType,
                        email,
                        new EventMessageParamsCancelTalon(
                                subject,
                                talonTime,
                                doctorFirstName,
                                doctorLastName,
                                departmentName,
                                organizationName)
                )
        );
    }
}
