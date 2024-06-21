package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.enums.CommandType;

public interface MessageServiceSender {

    void sendInMessageService(CommandType commandType,
                              String email,
                              String subject,
                              String talonTime,
                              String doctorFirstName,
                              String doctorLastName,
                              String departmentName,
                              String organizationName);
}
