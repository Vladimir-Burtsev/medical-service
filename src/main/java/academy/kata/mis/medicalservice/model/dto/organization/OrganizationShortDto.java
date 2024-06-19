package academy.kata.mis.medicalservice.model.dto.organization;

import lombok.Builder;

@Builder
public record OrganizationShortDto(long organizationId,
                                   String organizationName) {
}
