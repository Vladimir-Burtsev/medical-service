package academy.kata.mis.medicalservice.service;

public interface KafkaSenderService {
    void sendToKafkaAsync(String topic, Object message);
}
