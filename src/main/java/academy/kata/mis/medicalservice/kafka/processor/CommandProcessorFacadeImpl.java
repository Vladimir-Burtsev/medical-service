package academy.kata.mis.medicalservice.kafka.processor;

import academy.kata.mis.medicalservice.model.dto.kafka.Command;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommandProcessorFacadeImpl implements CommandProcessorFacade {

    private final Map<CommandType, CommandProcessor<? extends Command>> processorByCommandType;

    public CommandProcessorFacadeImpl(List<CommandProcessor<? extends Command>> processors) {
        processorByCommandType = processors.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toMap(CommandProcessor::getCommandType, Function.identity()),
                                Collections::unmodifiableMap
                        )
                );
    }

    @Override
    public <T extends Command> void process(T command) {
        //noinspection unchecked
        ((CommandProcessor<T>) Optional.ofNullable(processorByCommandType.get(command.getType()))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported command type: " + command.getType())))
                .process(command);
    }
}
