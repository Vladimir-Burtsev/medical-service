package academy.kata.mis.medicalservice.model.dto.organization.convertor;

import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationConvertor {
    public OrganizationShortDto entityToOrganizationShortDto(DepartmentAndOrganizationDto departmentAndOrganizationDto) {
        return OrganizationShortDto.builder()
                .organizationId(departmentAndOrganizationDto.organizationId())
                .organizationName(departmentAndOrganizationDto.organizationName())
                .build();
    }
}