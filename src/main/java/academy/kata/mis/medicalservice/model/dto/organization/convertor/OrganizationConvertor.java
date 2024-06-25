package academy.kata.mis.medicalservice.model.dto.organization.convertor;

import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationConvertor {

    public OrganizationShortDto entityToOrganizationShortDto(DepartmentAndOrganizationDto daoDto) {
        return OrganizationShortDto.builder()
                .organizationId(daoDto.organizationId())
                .organizationName(daoDto.organizationName())
                .build();
    }

    public OrganizationShortDto entityToOrganizationShortDto(DepartmentOrganizationPositionCabinetNameDto docDto) {
        return OrganizationShortDto.builder()
                .organizationId(docDto.organizationId())
                .organizationName(docDto.organizationName())
                .build();
    }


}
