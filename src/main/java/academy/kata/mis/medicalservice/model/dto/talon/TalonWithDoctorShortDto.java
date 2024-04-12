package academy.kata.mis.medicalservice.model.dto.talon;

import java.time.LocalDateTime;

public record TalonWithDoctorShortDto(long talonId,
                                      LocalDateTime visitTime,
                                      long doctorId,
                                      String doctorFirstName,
                                      String doctorLastName,
                                      String doctorPatronymic,
                                      String doctorPositionName) {
}
