package academy.kata.mis.medicalservice.model.dto.feign;

import lombok.Getter;
import lombok.Setter;

public record PersonDto(long id, String firstName, String lastName) {
}
