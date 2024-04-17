package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorWithTalonsDto;

import java.time.LocalDate;
import java.util.List;

public record CurrentDayTalonsByDepartmentResponse(LocalDate currentDate,
                                                   DepartmentShortDto department,
                                                   List<DoctorWithTalonsDto> doctors) {
}
