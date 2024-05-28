package academy.kata.mis.medicalservice.model.dto.kafka.message_service;

import academy.kata.mis.medicalservice.model.enums.CommandType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Setter
@Getter
public class EventMessage {
    @JsonProperty("type") private CommandType type;
    @JsonProperty("email") private String email;
    @JsonProperty("params") private Object params;
    @JsonProperty("operation_id") private UUID operationId;

    public EventMessage(CommandType type,
                        String email,
                        Object params) {
        this.type = type;
        this.email = email;
        this.params = params;
        this.operationId = UUID.randomUUID();
    }
}
