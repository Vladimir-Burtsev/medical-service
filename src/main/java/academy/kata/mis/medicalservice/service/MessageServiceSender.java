package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.enums.CommandType;

import java.time.LocalDateTime;

public interface MessageServiceSender {

    void sendInMessageService(CommandType commandType,
                              String email,
                              String subject,
                              LocalDateTime talonTime,
                              String doctorFirstName,
                              String doctorLastName,
                              String departmentName,
                              String organizationName);
}
