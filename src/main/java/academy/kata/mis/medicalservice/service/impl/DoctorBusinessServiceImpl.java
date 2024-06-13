package academy.kata.mis.medicalservice.service.impl;

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
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.OrganizationService;
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
        List<Doctor> doctors = doctorService.findAllByUserId(userId);

        if (doctors.isEmpty()) {
            return GetDoctorPersonalInfoResponse.builder()
                    .build();
        }

        PersonDto personDto = PersonDto.builder()
                .id(doctors.get(0).getPersonId())
                .firstName(null)
                .lastName(null)
                .build();

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

        Long departmentId = doctor.getDepartment().getId();

        Long organizationId = organizationService.getOrganizationIdByDepartmentId(departmentId);

        DepartmentShortDto departmentDto = DepartmentConvertor.entityToDepartmentShortDto(departmentId, null);

        OrganizationShortDto organizationDto = OrganizationConvertor.entityToOrganizationShortDto(organizationId, null);

        return EmployeeShortInfoInOrganizationDto.builder()
                .employeeId(doctor.getId())
                .positionName(null)
                .organization(organizationDto)
                .department(departmentDto)
                .build();

    }



    @Override
    public boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId) {
        return doctorService.existDoctorByUserIdAndDoctorId(userId, doctorId);
    }
}


