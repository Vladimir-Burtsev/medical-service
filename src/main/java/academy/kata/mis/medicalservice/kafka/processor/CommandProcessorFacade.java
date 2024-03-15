package academy.kata.mis.medicalservice.kafka.processor;

import academy.kata.mis.medicalservice.model.dto.kafka.Command;

public interface CommandProcessorFacade {

    <T extends Command> void process(T command) throws Exception;

}