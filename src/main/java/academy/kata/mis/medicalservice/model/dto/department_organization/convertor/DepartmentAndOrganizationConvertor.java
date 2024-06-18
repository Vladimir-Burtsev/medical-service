package academy.kata.mis.medicalservice.model.dto.department_organization.convertor;

import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;

public class DepartmentAndOrganizationConvertor {

    public DepartmentAndOrganizationDto entityToDepartmentAndOrganizationDto(Long departmentId, String departmentName, Long organizationId, String organizationName) {
        return DepartmentAndOrganizationDto.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .organizationId(organizationId)
                .organizationName(organizationName)
                .build();
    }
}
