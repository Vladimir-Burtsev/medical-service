package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.department.convertor.DepartmentConvertor;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.employee.EmployeeShortInfoInOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.convertor.OrganizationConvertor;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsDepartmentOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Department;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Organization;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.OrganizationService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorBusinessServiceImpl implements DoctorBusinessService {
    private final DoctorService doctorService;
    private final OrganizationService organizationService;
    private final DoctorConvertor doctorConvertor;
    private final DepartmentConvertor departmentConvertor;
    private final OrganizationConvertor organizationConvertor;
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

//        Department department = doctor.getDepartment();
//        Organization organization = department.getOrganization();

//        Long departmentId = department.getId();
//        Long organizationId = organization.getId();

//        String departmentName = structureFeignClient.getDepartmentById(departmentId).getName();
//        String organizationName = structureFeignClient.getOrganizationById(organizationId).getName();
//        String positionName = structureFeignClient.getPositionNameById(doctor.getPositionId()).getName();


//        DepartmentShortDto departmentDto = departmentConvertor.entityToDepartmentShortDto(departmentId, departmentName);
//        OrganizationShortDto organizationDto = organizationConvertor.entityToOrganizationShortDto(organizationId, organizationName);

        PositionsDepartmentOrganizationDto response = structureFeignClient.getPositionsDepartmentOrganizationByPositionId(doctor.getPositionId(), doctor.getDepartment().getId(), doctor.getDepartment().getOrganization().getId()).getBody();

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
}

