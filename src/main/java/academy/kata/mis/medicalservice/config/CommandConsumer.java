package academy.kata.mis.medicalservice.config;

import academy.kata.mis.medicalservice.model.dto.kafka.Command;
import academy.kata.mis.medicalservice.kafka.processor.CommandProcessorFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandConsumer {

    private final CommandProcessorFacade commandProcessorFacade;

    @KafkaListener(
            id = "${spring.kafka.consumer.group-id}",
            topics = {"#{'${spring.kafka.consumer.topic.block-token}'}"},
            containerFactory = "singleFactory")
    public void processCommand(Command command) {
        try {
            log.info("Consume: {}", command);
            commandProcessorFacade.process(command);
        } catch (Exception e) {
            log.error("Exception during processing command", e);
        }
    }
}
