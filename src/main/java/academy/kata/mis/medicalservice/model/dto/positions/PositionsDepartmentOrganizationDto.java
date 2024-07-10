package academy.kata.mis.medicalservice.model.dto.positions;

import lombok.Builder;
import lombok.Getter;


@Builder
public record PositionsDepartmentOrganizationDto(
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
