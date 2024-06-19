package academy.kata.mis.medicalservice.model.dto.doctor.convertor;

import academy.kata.mis.medicalservice.model.dto.PositionDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import org.springframework.stereotype.Component;

@Component
public class DoctorConvertor {
    public DoctorFullNameAndPositionsAndCabinetDto entityToDoctorFullNameAndPositionsAndCabinetDto(PersonFullNameDto personFullNameDto,
                                                   PositionsNameAndCabinetDto positionsNameAndCabinetDto) {
        return DoctorFullNameAndPositionsAndCabinetDto.builder()
                .doctorFirstName(personFullNameDto.firstName())
                .doctorLastName(personFullNameDto.lastName())
                .patronymic(personFullNameDto.patronymic())
                .positionsName(positionsNameAndCabinetDto.name())
                .cabinet(positionsNameAndCabinetDto.cabinet())
                .build();
    }

    public DoctorShortDto entityToDoctorShortDtoWithPositionName(DoctorShortDto doctorShortDto, PositionDto positionDto) {
        return DoctorShortDto.builder()
                .doctorId(doctorShortDto.doctorId())
                .doctorFirstName(doctorShortDto.doctorFirstName())
                .doctorLastName(doctorShortDto.doctorLastName())
                .patronymic(doctorShortDto.patronymic())
                .doctorPositionName(positionDto.name())
                .build();
    }

    public DoctorShortDto entityToDoctorShortDtoWithPositionName(DoctorShortDto doctorShortDto,
                                                                 PositionsNameAndCabinetDto positionsNameAndCabinetDto) {
        return DoctorShortDto.builder()
                .doctorId(doctorShortDto.doctorId())
                .doctorFirstName(doctorShortDto.doctorFirstName())
                .doctorLastName(doctorShortDto.doctorLastName())
                .patronymic(doctorShortDto.patronymic())
                .doctorPositionName(positionsNameAndCabinetDto.name())
                .build();
    }
}
