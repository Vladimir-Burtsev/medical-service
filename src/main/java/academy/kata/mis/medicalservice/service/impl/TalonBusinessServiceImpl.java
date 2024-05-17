package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.GetAssignedTalonsByPatientResponse;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.talon.converter.TalonConverter;
import academy.kata.mis.medicalservice.model.entity.Talon;
import academy.kata.mis.medicalservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalonBusinessServiceImpl implements TalonBusinessService {
    private final TalonService talonService;
    private final TalonConverter talonConverter;
    private final DoctorBusinessService doctorBusinessService;
    private final PersonService personService;
    private final PositionsService positionsService;

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
        Set<Talon> talons = talonService.allPatientTalonByPatientId(patientId);

        Set<Long> doctorId = talons.stream().map(talon -> talon.getDoctor().getPersonId()).collect(Collectors.toSet());
        Map<Long, PersonFullNameDto> doctorFullNameByID = personService.getPersonsFullName(doctorId);

        Set<Long> doctorPositionsId = talons.stream().map(talon -> talon.getDoctor().getPositionId()).collect(Collectors.toSet());
        Map<Long, PositionsNameAndCabinetDto> doctorPositionInfoByPositionsId =
                positionsService.getDoctorsPositionsNameAndCabinetByPositionsId(doctorPositionsId);

        return new GetAssignedTalonsByPatientResponse(
                talons.stream().map(talon -> talonConverter.entityToTalonWithDoctorShortDto(talon,
                        doctorBusinessService.getDoctorFullNameAndPositionsAndCabinet(
                                doctorFullNameByID.get(talon.getDoctor().getPersonId()),
                                doctorPositionInfoByPositionsId.get(talon.getDoctor().getPositionId())
                        )))
                        .collect(Collectors.toList())
        );
    }
}
