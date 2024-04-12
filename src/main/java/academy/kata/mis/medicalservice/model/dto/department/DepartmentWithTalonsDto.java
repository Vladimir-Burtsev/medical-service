package academy.kata.mis.medicalservice.model.dto.department;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;

import java.util.List;

public record DepartmentWithTalonsDto(long departmentId,
                                      String departmentName,
                                      List<TalonWithDoctorShortDto> talons) {
}
