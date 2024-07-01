package academy.kata.mis.medicalservice.model.dto.feign;

import lombok.Builder;

@Builder
public record PositionsDepartmentOrganizationDto(
        Long positionId,
        String positionName,
        Long departmentId,
        String departmentName,
        Long organizationId,
        String organizationName) {

}
