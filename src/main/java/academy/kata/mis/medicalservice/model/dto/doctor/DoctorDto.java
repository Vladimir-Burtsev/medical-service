package academy.kata.mis.medicalservice.model.dto.doctor;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentDto;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DoctorDto (Long id,
                         long personId,
                         long positionId,
                         UUID userId,
                         DepartmentDto department) {
}
