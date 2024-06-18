package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorBusinessService {
    Doctor existsByUserIdAndId(UUID doctorUUID, long id);

    DoctorFullNameAndPositionsAndCabinetDto getDoctorFullNameAndPositionsAndCabinet(PersonFullNameDto personFullNameDto,
                                                                                    PositionsNameAndCabinetDto positionsNameAndCabinetDto);
    boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId);

    //novikov
    boolean isDoctorExistsById(Long id);

    //novikov
    GetCurrentDoctorPersonalInfoResponse getCurrentDoctorPersonalInfoById(long id);
}
