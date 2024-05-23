package academy.kata.mis.medicalservice.model.dto.department_organization;

import lombok.Builder;

@Builder
public record DepartmentAndOrganizationDto(
        Long departmentId,
        String departmentName,
        Long organizationId,
        String organizationName) {
}
