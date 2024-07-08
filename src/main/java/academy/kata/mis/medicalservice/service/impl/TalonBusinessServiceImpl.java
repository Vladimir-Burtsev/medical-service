package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetFullTalonInformationResponse;
import academy.kata.mis.medicalservice.model.dto.department.convertor.DepartmentConvertor;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.organization.convertor.OrganizationConvertor;
import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;
import academy.kata.mis.medicalservice.model.dto.patient.convertor.PatientConvertor;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonsListDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.talon.CancelTalonDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorPatientInfoDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.talon.converter.TalonConverter;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import academy.kata.mis.medicalservice.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalonBusinessServiceImpl implements TalonBusinessService {
    private final TalonService talonService;
    private final PersonFeignClient personFeignClient;
    private final TalonConverter talonConverter;
    private final DoctorService doctorService;
    private final DepartmentConvertor departmentConvertor;
    private final OrganizationConvertor organizationConvertor;
    private final PatientConvertor patientConvertor;
    private final DoctorConvertor doctorConvertor;
    private final StructureFeignClient structureFeignClient;
    private final MessageServiceSender messageServiceSender;
    private final DepartmentService departmentService;

    @Override
    @Transactional
    public CancelTalonDto cancelReservationTalon(Long talonId, UUID userId) {
        Talon talon = talonService.findById(talonId).get();
        talon.setPatient(null);
        talonService.save(talon);

        PersonDto personDto = personFeignClient
                .getPersonById(doctorService.getDoctorPersonIdByTalonId(talonId));

        DepartmentAndOrganizationDto departmentAndOrganizationDto = structureFeignClient
                .getDepartmentAndOrganizationName(departmentService.getDepartmentIdByTalonId(talonId));

        messageServiceSender.sendInMessageService(
                CommandType.RESPONSE_TO_EMAIL_ABOUT_CANCEL_TALON,
                personFeignClient.getPersonEmailByUserId(userId),
                "отмена записи на прием к врачу",
                talon.getTime().toString(),
                personDto.firstName(),
                personDto.lastName(),
                departmentAndOrganizationDto.departmentName(),
                departmentAndOrganizationDto.organizationName()
        );

        return CancelTalonDto.builder()
                .talonId(talonId)
                .doctorId(talon.getDoctor().getId())
                .departmentId(departmentAndOrganizationDto.departmentId())
                .organizationId(departmentAndOrganizationDto.organizationId())
                .build();
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

    @Override
    public boolean existsTalonById(Long talonId) {
        return talonService.existsTalonById(talonId);
    }

    @Override
    public boolean isCurrentAuthDoctorAssignToTalonByUserIdAndTalonId(UUID userId, Long talonId) {
        return talonService.isCurrentAuthDoctorAssignToTalonByUserIdAndTalonId(userId, talonId);
    }

    @Override
    public GetFullTalonInformationResponse getFullTalonInfoById(Long talonId) {
        TalonWithDoctorPatientInfoDto talonInfoDto =
                talonService.getTalonWithDoctorPatientPersonsById(talonId);

        DepartmentOrganizationPositionCabinetNameDto depOrgPosCabDto = structureFeignClient
                .getDepartmentOrganizationPositionCabinetNameDto(
                        talonInfoDto.doctorPositionId()
                );

        Set<Long> personIds = new LinkedHashSet<>();
        personIds.add(talonInfoDto.doctorPersonId());

        if (talonInfoDto.patientPersonId() != null) {
            personIds.add(talonInfoDto.patientPersonId());
        }

        PersonsListDto personsListDto = personFeignClient.getPersonsListByIds(personIds);

        DoctorShortDto doctorShortDto = doctorConvertor
                .personToDoctorShortDtoWithPositionName(
                        talonInfoDto.doctorId(),
                        personsListDto.persons()
                                .stream()
                                .filter(p -> p.personId() == talonInfoDto.doctorPersonId())
                                .findFirst()
                                .get(),
                        depOrgPosCabDto.positionName()
                );

        PatientShortDto patientShortDto = (talonInfoDto.patientPersonId() == null) ? null
                : patientConvertor.personToPatientShortDto(
                        talonInfoDto.patientId(),
                        personsListDto.persons()
                                .stream()
                                .filter(p -> p.personId() == talonInfoDto.patientPersonId())
                                .findFirst()
                                .get()
                );

        return new GetFullTalonInformationResponse(
                talonId,
                talonInfoDto.talonTime(),
                organizationConvertor.entityToOrganizationShortDto(depOrgPosCabDto),
                departmentConvertor.entityToDepartmentShortDto(depOrgPosCabDto),
                depOrgPosCabDto.cabinetNumber(),
                doctorShortDto,
                patientShortDto
        );
    }
}
