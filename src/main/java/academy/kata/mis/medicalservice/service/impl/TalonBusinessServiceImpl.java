package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.talon.converter.TalonConverter;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        Set<Doctor> doctors = talons.stream().map(Talon::getDoctor).collect(Collectors.toSet());

        Map<Long, PersonFullNameDto> doctorFullNameByID = getDoctorFullNameByID(doctors
                                                                                .stream()
                                                                                .map(Doctor::getPersonId)
                                                                                .collect(Collectors.toSet()));

        Map<Long, PositionsNameAndCabinetDto> doctorPositionInfoByPositionsId =
                                        getDoctorPositionInfoByPositionsId(doctors
                                                                            .stream()
                                                                            .map(Doctor::getPositionId)
                                                                            .collect(Collectors.toSet()));

        Map<Long, DoctorFullNameAndPositionsAndCabinetDto> doctorFullNameAndPositionsAndCabinetDtoByDoctorId =
                getDoctorFullNameAndPositionsAndCabinetDtoByDoctorsId(doctors,
                                                                        doctorFullNameByID,
                                                                        doctorPositionInfoByPositionsId);

        return new GetAssignedTalonsByPatientResponse(
                talons.stream().map(talon -> talonConverter.entityToTalonWithDoctorShortDto(talon,
                        doctorFullNameAndPositionsAndCabinetDtoByDoctorId.get(talon.getDoctor().getId())))
                        .collect(Collectors.toList())
        );
    }

    private Map<Long, PersonFullNameDto> getDoctorFullNameByID(Set<Long> doctorPersonId) {
        Map<Long, PersonFullNameDto> personsFullNameById = new HashMap<>();
        doctorPersonId.forEach(id -> personsFullNameById.put(id, personFeignClient.getPersonFullNameDtoById(id)));
        return personsFullNameById;
    }

    private Map<Long, PositionsNameAndCabinetDto> getDoctorPositionInfoByPositionsId(Set<Long> positionsId) {
        Map<Long, PositionsNameAndCabinetDto> positionsNameAndCabinetByPositionsId = new HashMap<>();
        positionsId.forEach(id -> positionsNameAndCabinetByPositionsId.put(id,
                                                        structureFeignClient.getPositionsNameAndCabinetById(id)));
        return positionsNameAndCabinetByPositionsId;
    }

    private DoctorFullNameAndPositionsAndCabinetDto
            getDoctorFullNameAndPositionsAndCabinet(PersonFullNameDto personFullNameDtoMap,
                                                    PositionsNameAndCabinetDto positionsNameAndCabinetDtoMap) {
        return doctorConvertor.entityToDoctorFullNameAndPositionsAndCabinetDto(personFullNameDtoMap,
                                                                                positionsNameAndCabinetDtoMap);
    }

    private Map<Long, DoctorFullNameAndPositionsAndCabinetDto>
            getDoctorFullNameAndPositionsAndCabinetDtoByDoctorsId(Set<Doctor> doctorsId,
                                                                  Map<Long, PersonFullNameDto> personFullNameDtoMap,
                                                                  Map<Long, PositionsNameAndCabinetDto> positionsNameAndCabinetDtoMap) {
        Map<Long, DoctorFullNameAndPositionsAndCabinetDto> result = new HashMap<>();
        doctorsId.forEach(doctor -> result.put(doctor.getId(),
                getDoctorFullNameAndPositionsAndCabinet(personFullNameDtoMap.get(doctor.getPersonId()),
                                                        positionsNameAndCabinetDtoMap.get(doctor.getPositionId()))));
        return result;
    }
}
