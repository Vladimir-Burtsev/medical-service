package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;

import java.util.List;

public record GetDoctorsForDepartmentResponse(long departmentId,
                                              String departmentName,
                                              List<DoctorShortDto> doctors) {
}
