package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;

import java.util.Map;
import java.util.Set;

public interface PositionsService {
    PositionsNameAndCabinetDto getPositionsNameAndCabinetByPositionsId(long id);
    Map<Long, PositionsNameAndCabinetDto> getDoctorsPositionsNameAndCabinetByPositionsId (Set<Long> positionsId);
}
