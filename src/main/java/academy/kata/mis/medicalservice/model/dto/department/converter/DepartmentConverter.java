package academy.kata.mis.medicalservice.model.dto.department.converter;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentDto;
import academy.kata.mis.medicalservice.model.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter {
    public DepartmentDto entityToDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .organizationId(department.getOrganization().getId())
                .build();
    }
}
