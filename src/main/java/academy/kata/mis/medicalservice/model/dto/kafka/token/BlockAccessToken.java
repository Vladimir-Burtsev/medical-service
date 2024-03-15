package academy.kata.mis.medicalservice.model.dto.kafka.token;

import academy.kata.mis.medicalservice.model.dto.kafka.Command;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BlockAccessToken extends Command {
    private BlockedAccessToken params;

    public BlockAccessToken(
            @JsonProperty("type") CommandType type,
            @JsonProperty("params") BlockedAccessToken params) {
        super(type);
        this.params = params;
    }
}
