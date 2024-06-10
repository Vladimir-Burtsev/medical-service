package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.employee.EmployeeShortInfoInOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
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

    public GetDoctorPersonalInfoResponse getDoctorInformationByUser(UUID userId) {
        List<Doctor> doctors = doctorService.findAllByUserId(userId);


        if (doctors.isEmpty()) {
            return GetDoctorPersonalInfoResponse.builder()
                    .person(null)
                    .doctors(null)
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

    List<EmployeeShortInfoInOrganizationDto> createDoctors(List<Doctor> doctors) {
        return doctors.stream()
                .map(this::create)
                .toList();
    }

    EmployeeShortInfoInOrganizationDto create(Doctor doctor) {


        long organizationId = doctorService.getOrganizationIdByDepartmentId(doctor.getDepartment().getId());

        OrganizationShortDto organizationDto = OrganizationShortDto.builder()
                .organizationId(organizationId)
                .organizationName(null)
                .build();

        DepartmentShortDto departmentDto = DepartmentShortDto.builder()
                .departmentId(doctor.getDepartment().getId())
                .departmentName(null)
                .build();


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


