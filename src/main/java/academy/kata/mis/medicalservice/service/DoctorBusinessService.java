package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorBusinessService {
    Doctor getDoctorIfExists(UUID doctorUUID, long id);
    DoctorFullNameAndPositionsAndCabinetDto getDoctorFullNameAndPositionsAndCabinet(PersonFullNameDto personFullNameDto,
                                                                                    PositionsNameAndCabinetDto positionsNameAndCabinetDto);
}
