package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class DoctorBusinessServiceImpl implements DoctorBusinessService {

    private final DoctorService doctorService;

    @Override
    public void checkDoctorExistAndCurrent(long doctorId, UUID userId, long diseaseDepId) {
        if (!doctorService.isExistByIdAndUserId(doctorId, userId, diseaseDepId)) {
            log.error(String.format("Доктор с id=%s не найден или заболевание с id=%s из другого отделения",
                    doctorId,
                    diseaseDepId));
            throw new LogicException("Авторизованный пользователь не из указанного лечебного отделения");
        }
    }
}
