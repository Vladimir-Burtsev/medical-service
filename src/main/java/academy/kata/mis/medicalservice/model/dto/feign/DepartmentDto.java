package academy.kata.mis.medicalservice.model.dto.feign;

import lombok.Builder;

@Builder
public record DepartmentDto(long id, String name) {

    public String getName() {
        return name;
    }
}


