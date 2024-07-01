package academy.kata.mis.medicalservice.model.dto.department.convertor;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConvertor {

    public DepartmentShortDto entityToDepartmentShortDto(Long departmentId, String departmentName) {
        return DepartmentShortDto.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .build();
    }

    public DepartmentShortDto entityToDepartmentShortDto(DepartmentAndOrganizationDto daoDto) {
        return DepartmentShortDto.builder()
                .departmentId(daoDto.departmentId())
                .departmentName(daoDto.departmentName())
                .build();
    }

    public DepartmentShortDto entityToDepartmentShortDto(DepartmentOrganizationPositionCabinetNameDto docDto) {
        return DepartmentShortDto.builder()
                .departmentId(docDto.departmentId())
                .departmentName(docDto.departmentName())
                .build();
    }
}
