package academy.kata.mis.medicalservice.model.dto.doctor;

public record DoctorShortInfoInOrganizationDto(long organizationId,
                                               String organizationName,
                                               long departmentId,
                                               String departmentName,
                                               long doctorId,
                                               String positionName) {
}
