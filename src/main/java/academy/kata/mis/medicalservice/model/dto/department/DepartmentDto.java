package academy.kata.mis.medicalservice.model.dto.department;

import lombok.Builder;

@Builder
public record DepartmentDto (long id,
                             long organizationId){
}
