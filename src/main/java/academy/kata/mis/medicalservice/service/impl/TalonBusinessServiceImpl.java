package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.appeal.RequestSendAppealToReportService;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.KafkaSenderService;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import academy.kata.mis.medicalservice.service.TalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TalonBusinessServiceImpl implements TalonBusinessService {
    private final TalonService talonService;
    private final PersonFeignClient personFeignClient;

    @Override
    @Transactional
    public void cancelReservationTalon(Long talonId) {
        Talon talon = talonService.findById(talonId).get();
        talon.setPatient(null);
        talonService.save(talon);
    }

    @Override
    public boolean existsTalonByIdAndPatientUserId(Long talonId, UUID userId) {
        return talonService.existsTalonByIdAndPatientUserId(talonId, userId);
    }

    @Override
    public String getResponseTalonCancel(Long talonId) {
        Talon talon = talonService.findById(talonId).get();
        PersonDto personDto = personFeignClient.getPersonById(talonService.getDoctorPersonIdByTalonId(talonId));
        String response = String.format("""
                Запись на прием в %s к врачу %s %s отменена.
                """,
                talon.getTime(),
                personDto.firstName(),
                personDto.lastName()
        );
        return response;
    }
}
