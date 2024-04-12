package academy.kata.mis.medicalservice.model.dto.doctor;

public record DoctorForDepartmentShortDto(long doctorId,
                                          String doctorFirstName,
                                          String doctorLastName,
                                          String patronymic,
                                          String doctorPositionName) {
}
