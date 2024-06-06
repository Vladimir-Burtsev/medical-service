package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.repository.TalonRepository;
import academy.kata.mis.medicalservice.service.TalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Transactional
    public List<Talon> getAllByTomorrow() {
        return talonRepository.findAllByTomorrow();
    }
}
