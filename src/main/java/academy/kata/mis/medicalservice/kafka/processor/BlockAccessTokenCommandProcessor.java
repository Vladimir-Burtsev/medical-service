package academy.kata.mis.medicalservice.kafka.processor;

import academy.kata.mis.medicalservice.model.dto.kafka.token.BlockAccessToken;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import academy.kata.mis.medicalservice.service.AccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockAccessTokenCommandProcessor implements CommandProcessor<BlockAccessToken> {
    private final AccessTokenService accessTokenService;

    @Override
    public void process(BlockAccessToken command) {
        accessTokenService.addToBlock(command.getParams().getAccessToken());
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.BLOCK_ACCESS_TOKEN;
    }
}