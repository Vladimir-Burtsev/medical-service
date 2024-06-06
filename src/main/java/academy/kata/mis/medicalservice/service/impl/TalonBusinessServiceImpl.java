package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentDto;
import academy.kata.mis.medicalservice.model.dto.department.converter.DepartmentConverter;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.patient.PatientAndPersonIdDto;
import academy.kata.mis.medicalservice.model.dto.patient.convertor.PatientConvertor;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.talon.converter.TalonConverter;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalonBusinessServiceImpl implements TalonBusinessService {
    private final TalonService talonService;
    private final TalonConverter talonConverter;
    private final DoctorConvertor doctorConvertor;
    private final PersonFeignClient personFeignClient;
    private final StructureFeignClient structureFeignClient;
    private final DepartmentConverter departmentConverter;
    private final DoctorConvertor doctorConverter;
    private final PatientConvertor patientConvertor;

    @Override
    @Transactional
    public void cancelReservationTalon(Long talonId) {
        Talon talon = talonService.findById(talonId).get();
        talon.setPatient(null);
        talonService.save(talon);
    }

    @Override
    public boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId) {
        return talonService.existsTalonByIdAndPatientUserId(talonId, userId);
    }

    @Override
    public GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId) {
        Set<Talon> talons = talonService.allPatientTalonByPatientId(patientId);
        Set<Doctor> doctors = talons.stream()
                .map(Talon::getDoctor)
                .collect(Collectors.toSet());

        Map<Long, PersonFullNameDto> doctorFullNameByID = getDoctorFullNameByDoctor(doctors);

        Map<Long, PositionsNameAndCabinetDto> doctorPositionInfoByPositionsId =
                getDoctorPositionInfoByDoctor(doctors);

        Map<Long, DoctorFullNameAndPositionsAndCabinetDto> doctorFullNameAndPositionsAndCabinetDtoByDoctorId =
                getDoctorFullNameAndPositionsAndCabinetDtoByDoctorsId(
                        doctors,
                        doctorFullNameByID,
                        doctorPositionInfoByPositionsId
                );

        List<TalonWithDoctorShortDto> talonWithDoctorShortDtos =
                getTalonWithDoctorShortDto(
                        talons,
                        doctorFullNameAndPositionsAndCabinetDtoByDoctorId
                );

        return new GetAssignedTalonsByPatientResponse(talonWithDoctorShortDtos);
    }

    @Override
    public List<TalonDto> getAllByTomorrow() {
        List<Talon> talons = talonService.getAllByTomorrow();
        List<Doctor> doctors = talons.stream()
                .map(t -> t.getDoctor())
                .toList();

        Map<Long, PatientAndPersonIdDto> patientAndPersonIdDtoByPatientId = talons.stream()
                .collect(Collectors.toMap(talon -> talon.getPatient().getId(),
                        talon -> patientConvertor.entityToPatientAndPersonIdDto(talon.getPatient())));
        Map<Long, DepartmentDto> departmentDtoByDoctorId = doctors.stream()
                .collect(Collectors.toMap(Doctor::getId,
                        doctor -> departmentConverter.entityToDepartmentDto(doctor.getDepartment())));
        Map<Long, DoctorDto> doctorDtoByDoctorId = doctors.stream()
                .collect(Collectors.toMap(Doctor::getId,
                        doctor -> doctorConverter.entityToDoctorDto(doctor, departmentDtoByDoctorId.get(doctor.getId()))));

        List<TalonDto> result = talons.stream()
                .map(talon -> talonConverter.entityToTalonDto(talon,
                        doctorDtoByDoctorId.get(talon.getDoctor().getId()),
                        patientAndPersonIdDtoByPatientId.get(talon.getPatient().getId())))
                .toList();

        return result;
    }

    private List<TalonWithDoctorShortDto> getTalonWithDoctorShortDto(
            Set<Talon> talons,
            Map<Long, DoctorFullNameAndPositionsAndCabinetDto> doctorsMap) {
        return talons.stream()
                .map(
                        talon -> talonConverter.entityToTalonWithDoctorShortDto(
                                talon,
                                doctorsMap.get(talon.getDoctor().getId())
                        )
                )
                .collect(Collectors.toList());
    }

    private Map<Long, PersonFullNameDto> getDoctorFullNameByDoctor(Set<Doctor> doctors) {
        return doctors
                .stream()
                .map(Doctor::getPersonId)
                .map(personFeignClient::getPersonFullNameDtoById)
                .collect(Collectors.toMap(PersonFullNameDto::id, Function.identity()));
    }

    private Map<Long, PositionsNameAndCabinetDto> getDoctorPositionInfoByDoctor(Set<Doctor> doctors) {
        return doctors
                .stream()
                .map(Doctor::getPositionId)
                .map(structureFeignClient::getPositionsNameAndCabinetById)
                .collect(Collectors.toMap(PositionsNameAndCabinetDto::id, Function.identity()));
    }

    private DoctorFullNameAndPositionsAndCabinetDto getDoctorFullNameAndPositionsAndCabinet(
            PersonFullNameDto personFullNameDtoMap,
            PositionsNameAndCabinetDto positionsNameAndCabinetDtoMap) {
        return doctorConvertor.entityToDoctorFullNameAndPositionsAndCabinetDto(personFullNameDtoMap,
                                                                                positionsNameAndCabinetDtoMap);
    }

    private Map<Long, DoctorFullNameAndPositionsAndCabinetDto> getDoctorFullNameAndPositionsAndCabinetDtoByDoctorsId(
            Set<Doctor> doctorsId,
            Map<Long, PersonFullNameDto> personFullNameDtoMap,
            Map<Long, PositionsNameAndCabinetDto> positionsNameAndCabinetDtoMap) {
        return doctorsId.stream()
                .collect(
                        Collectors.toMap(
                                Doctor::getId,
                                it -> getDoctorFullNameAndPositionsAndCabinet(
                                        personFullNameDtoMap.get(it.getPersonId()),
                                        positionsNameAndCabinetDtoMap.get(it.getPositionId())
                                )
                        )
                );
    }
}
