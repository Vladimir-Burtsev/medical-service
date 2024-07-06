package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.repository.TalonRepository;
import academy.kata.mis.medicalservice.service.TalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TalonServiceImpl implements TalonService {
    private final TalonRepository talonRepository;

    @Override
    public Optional<Talon> findById(Long talonId) {
        return talonRepository.findById(talonId);
    }

    @Override
    public boolean existsTalonById(Long talonId) {
        return talonRepository.existsById(talonId);
    }

    @Override
    @Transactional
    public void save(Talon talon) {
        talonRepository.save(talon);
    }

    @Override
    public boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId) {
        return talonRepository.existsTalonByIdAndPatientUserId(talonId, userId);
    }

    @Override
    public Set<Talon> allPatientTalonByPatientId(long patientId) {
        return talonRepository.findAllByPatientId(patientId);
    }

    @Override
    public LocalDateTime getTalonTimeByTalonId(Long talonId) {
        return talonRepository.getTalonTimeByTalonId(talonId);
    }

    @Override
    public Optional<Long> getPatientIdByTalonId(Long talonId) {
        return talonRepository.getPatientIdByTalonId(talonId);
    }

    @Override
    public Optional<Long> getPatientPersonIdByTalonId(Long talonId) {
        return talonRepository.getPatientPersonIdByTalonId(talonId);
    }
}
