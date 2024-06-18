package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DepartmentService;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DoctorBusinessServiceImpl implements DoctorBusinessService {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final OrganizationService organizationService;

    public DoctorBusinessServiceImpl(DoctorService doctorService,
                                     DepartmentService departmentService,
                                     OrganizationService organizationService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
        this.organizationService = organizationService;
    }

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
    public boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId) {
        return doctorService.existDoctorByUserIdAndDoctorId(userId, doctorId);
    }

    //novikov
    @Override
    public boolean isDoctorExistsById(Long id) {
        return doctorService.isDoctorExistsById(id);
    }

    //novikov
    @Override
    public GetCurrentDoctorPersonalInfoResponse getCurrentDoctorPersonalInfoById(long id) {

        DoctorShortDto doctorShortDto = DoctorShortDto.builder()
                .doctorId(id)
                .doctorFirstName(null)
                .doctorLastName(null)
                .patronymic(null)
                .doctorPositionName(null)
                .build();

        OrganizationShortDto organizationShortDto = OrganizationShortDto.builder()
                .organizationId(organizationService.getOrganizationIdByDoctorId(id))
                .organizationName(null)
                .build();


        DepartmentShortDto departmentShortDto = DepartmentShortDto.builder()
                .departmentId(departmentService.getDepartmentIdByDoctorId(id))
                .departmentName(null)
                .build();

        String cabinetNumber = null;

        return new GetCurrentDoctorPersonalInfoResponse(doctorShortDto, organizationShortDto,
                departmentShortDto, cabinetNumber);
    }
}
