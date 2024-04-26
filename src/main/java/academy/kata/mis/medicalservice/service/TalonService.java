package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Talon;

import java.util.Optional;
import java.util.UUID;

public interface TalonService {
    Optional<Talon> findById(Long talonId);

    void save(Talon talon);

    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);
}
