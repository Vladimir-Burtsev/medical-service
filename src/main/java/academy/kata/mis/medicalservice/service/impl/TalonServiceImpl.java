package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.repository.TalonRepository;
import academy.kata.mis.medicalservice.service.TalonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalonServiceImpl implements TalonService {
    private final TalonRepository talonRepository;

    @Override
    public Optional<Talon> findById(Long talonId) {
        return talonRepository.findById(talonId);
    }

    @Override
    public void cancelReservationTalon(Talon talon) {
        talon.setPatient(null);
        talonRepository.save(talon);
    }
}
