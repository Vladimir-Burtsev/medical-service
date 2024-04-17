package academy.kata.mis.medicalservice.model.dto;

public record CreateAppealForPatientRequest(long doctorId,
                                            long patientId,
                                            long diseaseDepId) {
}
