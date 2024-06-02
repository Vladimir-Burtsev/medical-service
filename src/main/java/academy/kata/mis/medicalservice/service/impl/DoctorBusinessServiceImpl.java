package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DoctorBusinessServiceImpl implements DoctorBusinessService {
    private final DoctorService doctorService;

    public DoctorBusinessServiceImpl(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public Doctor getDoctorIfExists(UUID doctorUUID, long id) {
        return doctorService.existsByUserIdAndId(doctorUUID, id);
    }

    @Override
    public DoctorFullNameAndPositionsAndCabinetDto getDoctorFullNameAndPositionsAndCabinet(PersonFullNameDto personFullNameDto,
                                                                                           PositionsNameAndCabinetDto positionsNameAndCabinetDto) {
        return DoctorFullNameAndPositionsAndCabinetDto.builder()
                .doctorFirstName(personFullNameDto.firstName())
                .doctorLastName(personFullNameDto.lastName())
                .patronymic(personFullNameDto.patronymic())
                .positionsName(positionsNameAndCabinetDto.name())
                .cabinet(positionsNameAndCabinetDto.cabinet())
                .build();
    }

    @Override
    public boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId) {
        return doctorService.existDoctorByUserIdAndDoctorId(userId, doctorId);
    }
}
