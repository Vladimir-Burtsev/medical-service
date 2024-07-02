package academy.kata.mis.medicalservice.model.dto.feign;

import lombok.Builder;

@Builder
public record OrganizationDto(long id, String name) {
}
