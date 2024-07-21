package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.department.convertor.DepartmentConvertor;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.employee.EmployeeShortInfoInOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.convertor.OrganizationConvertor;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.positions.RepPositionsDepartmentOrganizationDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.VisitService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorBusinessServiceImpl implements DoctorBusinessService {

    private final DoctorService doctorService;
    private final VisitService visitService;
    private final DepartmentConvertor departmentConvertor;
    private final OrganizationConvertor organizationConvertor;
    private final DoctorConvertor doctorConvertor;
    private final StructureFeignClient structureFeignClient;
    private final PersonFeignClient personFeignClient;

    @Override
    public Doctor existsByUserIdAndId(UUID doctorUUID, long id) {
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
    public GetDoctorPersonalInfoResponse getDoctorInformationByUser(UUID userId) {
        List<Doctor> doctors = doctorService.findAllWithDepartmentsAndOrganizations(userId);

        if (doctors.isEmpty()) {
            return GetDoctorPersonalInfoResponse.builder()
                    .build();
        }

        long personId = doctors.get(0).getPersonId();
        PersonDto personDto = personFeignClient.getPersonById(personId);

        List<EmployeeShortInfoInOrganizationDto> doctorDtos = createDoctors(doctors);

        return GetDoctorPersonalInfoResponse.builder()
                .person(personDto)
                .doctors(doctorDtos)
                .build();
    }

    private List<EmployeeShortInfoInOrganizationDto> createDoctors(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::create)
                .toList();
    }

    private EmployeeShortInfoInOrganizationDto create(Doctor doctor) {

        RepPositionsDepartmentOrganizationDto response = structureFeignClient.getRepPositionsDepartmentOrganizationByPositionId(doctor.getPositionId());

        String positionName = null;
        if (response != null) {
            positionName = response.getPositionName();
        }

        DepartmentShortDto departmentDto = null;
        if (response != null) {
            departmentDto = departmentConvertor.entityToDepartmentShortDto(response.getDepartmentId(), response.getDepartmentName());
        }
        OrganizationShortDto organizationDto = null;
        if (response != null) {
            organizationDto = organizationConvertor.entityToOrganizationShortDto(response.getOrganizationId(), response.getOrganizationName());
        }

        return EmployeeShortInfoInOrganizationDto.builder()
                .employeeId(doctor.getId())
                .positionName(positionName)
                .organization(organizationDto)
                .department(departmentDto)
                .build();

    }

    @Override
    public boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId) {
        return doctorService.existDoctorByUserIdAndDoctorId(userId, doctorId);
    }

    @Override
    public boolean isDoctorExistsById(Long id) {
        return doctorService.isDoctorExistsById(id);
    }

    @Override
    public GetCurrentDoctorPersonalInfoResponse getCurrentDoctorPersonalInfoById(long doctorId) {

        DepartmentOrganizationPositionCabinetNameDto departmentOrganizationPositionCabinetNameDto = structureFeignClient
                .getDepartmentOrganizationPositionCabinetNameDto(doctorService.getPositionIdByDoctorId(doctorId));

        DoctorShortDto doctorShortDto = doctorConvertor.entityToDoctorShortDtoWithPositionName(
                personFeignClient.getDoctorShortDtoByPersonIdAndDoctorId(doctorService.getPersonIdByDoctorId(doctorId),doctorId),
                departmentOrganizationPositionCabinetNameDto);

        OrganizationShortDto organizationShortDto = organizationConvertor
                .entityToOrganizationShortDto(departmentOrganizationPositionCabinetNameDto);
        DepartmentShortDto departmentShortDto = departmentConvertor
                .entityToDepartmentShortDto(departmentOrganizationPositionCabinetNameDto);

        String cabinetNumber = departmentOrganizationPositionCabinetNameDto.cabinetNumber();

        return new GetCurrentDoctorPersonalInfoResponse(doctorShortDto, organizationShortDto,
                departmentShortDto, cabinetNumber);
    }

    @Override
    public boolean areDoctorsInSameDepartment(long visitId, UUID doctorUUID) {
        Doctor currentDoctor = Optional.ofNullable(doctorService.findDoctorByUUID(doctorUUID)).orElseThrow(
                () -> new EntityNotFoundException("Doctor already exists"));
        log.debug("Doctor with doctor_id {} already exists", doctorUUID);
        Visit visit = visitService.findVisitById(visitId);
        Doctor visitDoctor = visit.getDoctor();
        return currentDoctor.getDepartment().equals(visitDoctor.getDepartment());
    }
}
