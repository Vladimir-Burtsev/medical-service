package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorPatientInfoDto;
import academy.kata.mis.medicalservice.model.entity.Talon;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TalonService {
    Optional<Talon> findById(Long talonId);
    boolean existsTalonById(Long talonId);
    void save(Talon talon);
    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
    Set<Talon> allPatientTalonByPatientId(long patientId);
    boolean isCurrentAuthDoctorAssignToTalonByUserIdAndTalonId(UUID userId, Long talonId);
    TalonWithDoctorPatientInfoDto getTalonWithDoctorPatientPersonsById(Long talonId);
}
