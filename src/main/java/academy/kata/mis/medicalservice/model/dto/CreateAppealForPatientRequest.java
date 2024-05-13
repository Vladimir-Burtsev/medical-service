package academy.kata.mis.medicalservice.model.dto;

import academy.kata.mis.medicalservice.model.enums.InsuranceType;
import jakarta.validation.constraints.NotNull;

public record CreateAppealForPatientRequest(long doctorId,
                                            long patientId,
                                            long diseaseDepId,
                                            @NotNull InsuranceType insuranceType) {
}
