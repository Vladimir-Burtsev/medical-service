package academy.kata.mis.medicalservice.model.dto.doctor;

import lombok.Builder;

@Builder
public record DoctorFullNameAndPositionsAndCabinetDto(long doctorId,
                                                      String doctorFirstName,
                                                      String doctorLastName,
                                                      String patronymic,
                                                      String positionsName,
                                                      String cabinet
) {
}
