package academy.kata.mis.medicalservice.model.dto.positions;

import lombok.Builder;


@Builder
public record RepPositionsDepartmentOrganizationDto(
        Long positionId,
        String positionName,
        Long departmentId,
        String departmentName,
        Long organizationId,
        String organizationName) {

    public Long getOrganizationId() {
        return organizationId;
    }


    public String getOrganizationName() {
        return organizationName;
    }


    public Long getDepartmentId() {
        return departmentId;
    }


    public String getDepartmentName() {
        return departmentName;
    }


    public Long getPositionId() {
        return positionId;
    }


    public String getPositionName() {
        return positionName;
    }
}
