package academy.kata.mis.medicalservice.model.dto.person;

import lombok.Builder;

import java.util.List;

@Builder
public record PersonsListDto(List<PersonFullNameBirthdayDto> persons) {
}
