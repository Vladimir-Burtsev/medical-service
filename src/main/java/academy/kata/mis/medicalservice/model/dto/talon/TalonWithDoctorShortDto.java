package academy.kata.mis.medicalservice.model.dto.talon;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TalonWithDoctorShortDto(long talonId,
                                      LocalDateTime visitTime,
                                      long doctorId,
                                      String doctorFirstName,
                                      String doctorLastName,
                                      String doctorPatronymic,
                                      String doctorPositionName,
                                      String cabinet) {
}
