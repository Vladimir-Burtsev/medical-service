package academy.kata.mis.medicalservice.model.dto.department;

import lombok.Builder;

@Builder

public record DepartmentShortDto(long departmentId,
                                 String departmentName) {
}
