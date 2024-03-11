package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.KafkaSenderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaSenderServiceImpl implements KafkaSenderService {
    private ProducerRecord<String, Object> producerRecord;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaSenderServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    @Override
    public void sendToKafkaAsync(String topic, Object message) {
        sendMessage(topic, message);
    }

    private void sendMessage(String topic, Object message) {
        producerRecord = new ProducerRecord<>(topic, message);
        kafkaTemplate.send(producerRecord);
    }
}
