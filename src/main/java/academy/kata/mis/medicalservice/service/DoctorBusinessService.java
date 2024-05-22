package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.PositionDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorBusinessService {
    Doctor getDoctorIfExists(UUID doctorUUID, long id);
    DoctorShortDto getFullDoctorShortDto(DoctorShortDto doctorShortDto, PositionDto positionDto);
}
