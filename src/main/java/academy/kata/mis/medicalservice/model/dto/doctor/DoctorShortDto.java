package academy.kata.mis.medicalservice.model.dto.doctor;

import lombok.Builder;

@Builder
public record DoctorShortDto(long doctorId,
                             String doctorFirstName,
                             String doctorLastName,
                             String patronymic,
                             String doctorPositionName) {
}
