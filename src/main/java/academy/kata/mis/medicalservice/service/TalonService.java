package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Talon;

import java.util.Optional;

public interface TalonService {
    Optional<Talon> findById(Long talonId);

    void cancelReservationTalon(Talon talon);
}
