package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetFullTalonInformationResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.talon.CancelTalonDto;
import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.talon.converter.TalonConverter;
import academy.kata.mis.medicalservice.model.entity.Department;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Patient;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.model.enums.CommandType;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.MessageServiceSender;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import academy.kata.mis.medicalservice.service.TalonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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
    private final DoctorConvertor doctorConvertor;
    private final StructureFeignClient structureFeignClient;
    private final MessageServiceSender messageServiceSender;

    @Override
    @Transactional
    public CancelTalonDto cancelReservationTalon(Long talonId, UUID userId) {
        Talon talon = talonService.findById(talonId).get();
        talon.setPatient(null);
        talonService.save(talon);

        PersonDto personDto = personFeignClient
                .getPersonById(doctorService.getDoctorPersonIdByTalonId(talonId));

        DepartmentAndOrganizationDto departmentAndOrganizationDto = structureFeignClient
                .getDepartmentAndOrganizationName(doctorService.getDoctorIdByTalonId(talonId));

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

    @Override
    public boolean isExistById(Long talonId) {
        return talonService.findById(talonId).isPresent();
    }

    @Override
    public GetFullTalonInformationResponse getFullTalonInfoById(Long talonId) {
        Talon talon = talonService.findById(talonId).get();
        Doctor doctor = talon.getDoctor();
        Department department = doctor.getDepartment();
        Patient patient = talon.getPatient();

        OrganizationShortDto organizationShortDto = OrganizationShortDto.builder()
                .organizationId(department.getOrganization().getId())
                .organizationName(null)
                .build();

        DepartmentShortDto departmentShortDto = DepartmentShortDto.builder()
                .departmentId(department.getId())
                .departmentName(null)
                .build();

        DoctorShortDto doctorShortDto = DoctorShortDto.builder()
                .doctorId(doctor.getId())
                .doctorFirstName(null)
                .doctorLastName(null)
                .patronymic(null)
                .doctorPositionName(null)
                .build();

        PatientShortDto patientShortDto = (patient == null) ? null
                : PatientShortDto.builder()
                    .patientId(patient.getId())
                    .patientFirstName(null)
                    .patientLastname(null)
                    .patientPatronymic(null)
                    .birthday(null)
                    .build();

        return new GetFullTalonInformationResponse(
                talonId,
                talon.getTime(),
                organizationShortDto,
                departmentShortDto,
                null,
                doctorShortDto,
                patientShortDto
        );
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
