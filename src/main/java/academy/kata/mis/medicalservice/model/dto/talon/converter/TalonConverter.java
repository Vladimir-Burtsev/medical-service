package academy.kata.mis.medicalservice.model.dto.talon.converter;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;
import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.stereotype.Component;

@Component
public class TalonConverter {

    public TalonWithDoctorShortDto entityToTalonWithDoctorShortDto(Talon talon,
                                                                   DoctorFullNameAndPositionsAndCabinetDto doctorFullNameAndPositionsAndCabinetDto) {
        return TalonWithDoctorShortDto.builder()
                .talonId(talon.getId())
                .visitTime(talon.getTime())
                .doctorId(talon.getDoctor().getId())
                .doctorFirstName(doctorFullNameAndPositionsAndCabinetDto.doctorFirstName())
                .doctorLastName(doctorFullNameAndPositionsAndCabinetDto.doctorLastName())
                .doctorPositionName(doctorFullNameAndPositionsAndCabinetDto.positionsName())
                .doctorPatronymic(doctorFullNameAndPositionsAndCabinetDto.patronymic())
                .cabinet(doctorFullNameAndPositionsAndCabinetDto.cabinet())
                .build();
    }
}
