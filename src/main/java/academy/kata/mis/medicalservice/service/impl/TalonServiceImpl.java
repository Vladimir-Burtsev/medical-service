package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.AuthException;
import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.repository.TalonRepository;
import academy.kata.mis.medicalservice.service.TalonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalonServiceImpl implements TalonService {
    private final TalonRepository talonRepository;

    @Override
    public Talon findById(Long talonId) {
        String operation = "Возврат талона из базы данных";
        log.info("{}; talonID {}", operation, talonId);
        if (talonId < 0) {
            log.warn("{}; ошибка: указан некорректный Id талона; talonId {}", operation, talonId);
            throw new IllegalArgumentException("Указан некорректный Id = " + talonId + " талона.");
        }

        Optional<Talon> talon = talonRepository.findById(talonId);
        if (talon.isEmpty()) {
            log.warn("{}; ошибка: талон с указанным Id не найден; talonId {}", operation, talonId);
            throw new IllegalArgumentException("Талон с Id = " + talonId + " не сущестует.");
        }
        log.debug("{}; Успешно; talonID {}", operation, talonId);
        System.out.println("ТАЛОН с ID = " + talonId + " ВЕРНУЛИ!!!!!!!!!!!!");
        return talon.get();
    }

    @Override
    public void cancelReservationTalon(Long talonId, UUID userIdAuth) {
        Talon talon = this.findById(talonId);
        UUID userIdTalon = talon.getPatient().getUserId();

        if (!userIdTalon.equals(userIdAuth)) {
            log.warn("Отмена записи на прием к врачу; Ошибка: авторизованный пользователь не является владельцем талона; userIdAuth {}; talonId {}", userIdAuth, talonId);
            throw new AuthException("Авторизованный пользователь не является владельцем талона.");
        }

        talon.setPatient(null);
        talonRepository.save(talon);
    }
}
