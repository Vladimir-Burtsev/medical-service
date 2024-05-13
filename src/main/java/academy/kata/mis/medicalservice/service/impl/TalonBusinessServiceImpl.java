package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.talon.TalonWithDoctorShortDto;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.TalonBusinessService;
import academy.kata.mis.medicalservice.service.TalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TalonBusinessServiceImpl implements TalonBusinessService {
    private final TalonService talonService;

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
    public GetAssignedTalonsByPatientResponse getAllPatientTalonByPatientId(long patientId) {
        Set<Talon> patientTalons = talonService.allPatientTalonByPatientId(patientId);
        List<TalonWithDoctorShortDto> talonsWithDoctorShortDto = new ArrayList<>();
        for (Talon talon : patientTalons) {
            talonsWithDoctorShortDto.add(TalonWithDoctorShortDto.builder()
                    .talonId(talon.getId())
                    .visitTime(talon.getTime())
                    .doctorId(talon.getDoctor().getId())
                    .doctorFirstName(null)
                    .doctorLastName(null)
                    .doctorPositionName(null)
                    .doctorPatronymic(null)
                    .cabinet(null)
                    .build());
        }
        return new GetAssignedTalonsByPatientResponse(talonsWithDoctorShortDto);
    }
}
