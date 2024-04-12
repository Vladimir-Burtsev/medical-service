package academy.kata.mis.medicalservice.model.dto.doctor;

public record DoctorShortDto(long doctorId,
                             String doctorFirstName,
                             String doctorLastName,
                             String patronymic,
                             String doctorPositionName) {
}
