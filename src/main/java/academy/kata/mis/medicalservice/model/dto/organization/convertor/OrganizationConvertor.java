package academy.kata.mis.medicalservice.model.dto.organization.convertor;

import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationConvertor {

    public OrganizationShortDto entityToOrganizationShortDto(Long organizationId, String organizationName){
        return OrganizationShortDto.builder()
                .organizationId(organizationId)
                .organizationName(organizationName)
                .build();
    }


}