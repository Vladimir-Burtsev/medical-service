package academy.kata.mis.medicalservice.kafka.processor;

import academy.kata.mis.medicalservice.model.dto.kafka.Command;
import academy.kata.mis.medicalservice.model.enums.CommandType;

public interface CommandProcessor<T extends Command> {

    void process(T command);

    CommandType getCommandType();
}