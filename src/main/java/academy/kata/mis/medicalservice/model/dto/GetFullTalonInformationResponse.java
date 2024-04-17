package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.dto.department.DepartmentShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.organization.OrganizationShortDto;
import academy.kata.mis.medicalservice.model.dto.patient.PatientShortDto;

import java.time.LocalDate;

public record GetFullTalonInformationResponse(long talonId,
                                              LocalDate visitTime,
                                              OrganizationShortDto organization,
                                              DepartmentShortDto department,
                                              String cabinetNumber,
                                              DoctorShortDto doctor,
                                              PatientShortDto patient) {
}
