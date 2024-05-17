package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.StructureFeignClient;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.service.PositionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionsServiceImpl implements PositionsService {
    private final StructureFeignClient structureFeignClient;

    @Override
    public PositionsNameAndCabinetDto getPositionsNameAndCabinetByPositionsId(long id) {
        return structureFeignClient.getPositionsNameAndCabinetById(id);
    }

    @Override
    public Map<Long, PositionsNameAndCabinetDto> getDoctorsPositionsNameAndCabinetByPositionsId (Set<Long> positionsId) {
        Map<Long, PositionsNameAndCabinetDto> doctorsInfo = new HashMap<>();
        positionsId.forEach(id -> doctorsInfo.put(id, getPositionsNameAndCabinetByPositionsId(id)));
        return doctorsInfo;
    }
}
