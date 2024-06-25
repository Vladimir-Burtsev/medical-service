package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.GetCurrentDoctorPersonalInfoResponse;
import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.department.convertor.DepartmentConvertor;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorFullNameAndPositionsAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.convertor.OrganizationConvertor;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorBusinessServiceImpl implements DoctorBusinessService {
    private final DoctorService doctorService;
    private final StructureFeignClient structureFeignClient;
    private final OrganizationConvertor organizationConvertor;
    private final DepartmentConvertor departmentConvertor;
    private final DoctorConvertor doctorConvertor;
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
    public boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId) {
        return doctorService.existDoctorByUserIdAndDoctorId(userId, doctorId);
    }

    @Override
    public boolean isDoctorExistsById(Long id) {
        return doctorService.isDoctorExistsById(id);
    }

    @Override
    @SneakyThrows
    public GetCurrentDoctorPersonalInfoResponse getCurrentDoctorPersonalInfoById(long doctorId) {

            DepartmentOrganizationPositionCabinetNameDto departmentOrganizationPositionCabinetNameDto = structureFeignClient
                    .getDepartmentOrganizationPositionCabinetNameDto(doctorService.getPositionIdByDoctorId(doctorId));

            DoctorShortDto doctorShortDto = doctorConvertor
                    .entityToDoctorShortDtoWithPositionName(personFeignClient.getCurrentDoctorById(doctorId),
                            departmentOrganizationPositionCabinetNameDto);

        OrganizationShortDto organizationShortDto = organizationConvertor
                .entityToOrganizationShortDto(departmentOrganizationPositionCabinetNameDto);
        DepartmentShortDto departmentShortDto = departmentConvertor
                .entityToDepartmentShortDto(departmentOrganizationPositionCabinetNameDto);

        String cabinetNumber = departmentOrganizationPositionCabinetNameDto.cabinetNumber();

        return new GetCurrentDoctorPersonalInfoResponse(doctorShortDto, organizationShortDto,
                departmentShortDto, cabinetNumber);
    }
}
