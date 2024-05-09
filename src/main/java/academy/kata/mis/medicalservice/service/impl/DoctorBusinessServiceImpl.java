package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.service.DoctorBusinessService;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DoctorBusinessServiceImpl implements DoctorBusinessService {
    private final DoctorService doctorService;

    public DoctorBusinessServiceImpl(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @Override
    public Doctor getDoctorIfExists(UUID doctorUUID, long id) {
        if (doctorService.findByUserId(doctorUUID) == null) {
            log.error("Доктор с id:{}; не найден или авторизованный пользователь не является переданным доктором.",
                    doctorUUID);
            throw new LogicException("Доктор не найден");
        }
        if (!doctorService.findByUserId(doctorUUID).getId().equals(id)) {
            log.error("Авторизованный пользователь не является переданным доктором userId={}; doctorId={}.", doctorUUID, id);
            throw new LogicException("Доктор не найден");
        }

        return doctorService.findByUserId(doctorUUID);
    }
}
