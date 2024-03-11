package academy.kata.mis.medicalservice.dto.kafka.audit;

import academy.kata.mis.medicalservice.model.enums.CommandType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SaveAuditEvent extends Command {
    @JsonProperty("params") private AuditEventMessage params;

    public SaveAuditEvent(CommandType type,
                          AuditEventMessage params) {
        super(type);
        this.params = params;
    }
}
