package academy.kata.mis.medicalservice.model.dto;

public record AssignPatientToTalonRequest(long talonId,
                                          long patientId) {
}
