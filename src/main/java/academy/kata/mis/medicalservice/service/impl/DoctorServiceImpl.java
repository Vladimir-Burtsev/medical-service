package academy.kata.mis.medicalservice.service.impl;

<<<<<<< HEAD
import academy.kata.mis.medicalservice.repository.DoctorRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
=======
import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.repository.DoctorRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor existsByUserIdAndId(UUID doctorUUID, long id) {
        if (!doctorRepository.existsByUserIdAndId(doctorUUID, id)) {
            log.error("Доктор с id:{}; не найден или авторизованный пользователь не является переданным доктором.",
                    doctorUUID);
            throw new LogicException("Доктор не найден");
        }
        return doctorRepository.findByUserId(doctorUUID);
    }
}
