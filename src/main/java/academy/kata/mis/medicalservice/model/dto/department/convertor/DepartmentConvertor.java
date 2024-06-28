package academy.kata.mis.medicalservice.model.dto.department.convertor;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConvertor {
    public DepartmentShortDto entityToDepartmentShortDto(DepartmentAndOrganizationDto departmentAndOrganizationDto) {
        return DepartmentShortDto.builder()
                .departmentId(departmentAndOrganizationDto.departmentId())
                .departmentName(departmentAndOrganizationDto.departmentName())
                .build();
    }
}