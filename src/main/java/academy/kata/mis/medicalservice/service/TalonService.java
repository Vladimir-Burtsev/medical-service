package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Talon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TalonService {
    Optional<Talon> findById(Long talonId);

    void save(Talon talon);

    boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId);

    Long getDoctorPersonIdByTalonId(Long talonId);

    Set<Talon> allPatientTalonByPatientId(long patientId);

    Long getDoctorIdByTalonId(Long talonId);
}
