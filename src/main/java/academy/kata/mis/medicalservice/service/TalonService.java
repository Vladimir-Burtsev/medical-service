package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Talon;

import java.security.Principal;
import java.util.UUID;

public interface TalonService {
    Talon findById(Long talonId);

    void cancelReservationTalon(Long talonId, UUID userIdAuth);
}
