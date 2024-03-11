package academy.kata.mis.medicalservice.dto.kafka.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class AuditEventMessage {
    @JsonProperty("source_system") private String sourceSystem;
    @JsonProperty("initiator") private String initiator;
    @JsonProperty("operation") private String operation;
    @JsonProperty("event_time") private LocalDateTime eventTime;
    @JsonProperty("message") private String message;

    public AuditEventMessage(String sourceSystem,
                             String initiator,
                             String operation,
                             LocalDateTime eventTime,
                             String message) {
        this.sourceSystem = sourceSystem;
        this.initiator = initiator;
        this.operation = operation;
        this.eventTime = eventTime;
        this.message = message;
    }
}
