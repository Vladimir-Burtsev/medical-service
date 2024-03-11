package academy.kata.mis.medicalservice.dto.kafka.audit;

import academy.kata.mis.medicalservice.model.enums.CommandType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "SAVE_AUDIT_EVENT", value = SaveAuditEvent.class),
})
@Getter
@ToString
public class Command {
    @JsonProperty("type") private final CommandType type;
    @JsonProperty("operation_id") private final UUID operationId;

    public Command(CommandType type) {
        this.type = type;
        this.operationId = UUID.randomUUID();
    }
}
