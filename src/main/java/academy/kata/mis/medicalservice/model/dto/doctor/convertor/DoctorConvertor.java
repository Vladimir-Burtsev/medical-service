package academy.kata.mis.medicalservice.model.dto.doctor.convertor;

import academy.kata.mis.medicalservice.model.dto.PositionDto;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameBirthdayDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
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

    public DoctorShortDto entityToDoctorShortDtoWithPositionName(Long doctorId,
                                                                 PersonFullNameDto personFullNameDto,
                                                                 PositionDto positionDto) {
        return DoctorShortDto.builder()
                .doctorId(doctorId)
                .doctorFirstName(personFullNameDto.firstName())
                .doctorLastName(personFullNameDto.lastName())
                .patronymic(personFullNameDto.patronymic())
                .doctorPositionName(positionDto.name())
                .build();
    }

    public DoctorShortDto personToDoctorShortDtoWithPositionName(Long doctorId,
                                                                 PersonFullNameBirthdayDto doctorPerson,
                                                                 String positionName) {
        return DoctorShortDto.builder()
                .doctorId(doctorId)
                .doctorFirstName(doctorPerson.firstName())
                .doctorLastName(doctorPerson.lastName())
                .patronymic(doctorPerson.patronymic())
                .doctorPositionName(positionName)
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

    public DoctorShortDto entityToDoctorShortDtoWithPositionName(DoctorShortDto doctorShortDto,
                                                                 DepartmentOrganizationPositionCabinetNameDto dopDto) {
        return DoctorShortDto.builder()
                .doctorId(doctorShortDto.doctorId())
                .doctorFirstName(doctorShortDto.doctorFirstName())
                .doctorLastName(doctorShortDto.doctorLastName())
                .patronymic(doctorShortDto.patronymic())
                .doctorPositionName(dopDto.positionName())
                .build();
    }

    public DoctorDto entityToDoctorDto(Doctor doctor,
                                       DepartmentDto departmentDto) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .personId(doctor.getPersonId())
                .positionId(doctor.getPositionId())
                .userId(doctor.getUserId())
                .department(departmentDto)
                .build();
    }
    public DoctorShortDto convertDoctorToDoctorShortDto(Doctor doctor) {
        /**
         * ToDo
         * получить недостающие данные через feign client
         */
        return DoctorShortDto.builder()
                .doctorId(doctor.getId())
                .doctorFirstName("DoctorFirstName")
                .doctorLastName("DoctorLastName")
                .patronymic("Patronymic")
                .doctorPositionName("DoctorPositionName")
                .build();
    }
}
