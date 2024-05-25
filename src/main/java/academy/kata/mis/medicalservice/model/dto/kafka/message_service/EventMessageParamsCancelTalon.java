package academy.kata.mis.medicalservice.model.dto.kafka.message_service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class EventMessageParamsCancelTalon {
    @JsonProperty("subject") private String subject;
    @JsonProperty("talon_time") private LocalDateTime talonTime;
    @JsonProperty("doctor_first_name") private String doctorFirstName;
    @JsonProperty("doctor_last_name") private String doctorLastName;
    @JsonProperty("department_name") private String departmentName;
    @JsonProperty("organization_name") private String organizationName;

    public EventMessageParamsCancelTalon(String subject,
                                         LocalDateTime talonTime,
                                         String doctorFirstName,
                                         String doctorLastName,
                                         String departmentName,
                                         String organizationName) {
        this.talonTime = talonTime;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.departmentName = departmentName;
        this.organizationName = organizationName;
        this.subject = subject;
    }
}