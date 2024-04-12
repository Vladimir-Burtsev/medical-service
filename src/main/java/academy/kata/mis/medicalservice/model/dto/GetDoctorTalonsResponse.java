package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.talon.TalonShortDto;

import java.util.List;

public record GetDoctorTalonsResponse(long doctorId,
                                      String doctorFirstName,
                                      String doctorLastName,
                                      String doctorPatronymic,
                                      String doctorPositionName,
                                      List<TalonShortDto> talons) {
}


