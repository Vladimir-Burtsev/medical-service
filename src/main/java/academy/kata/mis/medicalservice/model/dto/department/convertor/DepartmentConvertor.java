package academy.kata.mis.medicalservice.model.dto.department.convertor;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConvertor {

    public static DepartmentShortDto entityToDepartmentShortDto(Long departmentId, String departmentName) {
        return DepartmentShortDto.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .build();
    }

}
