package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.exceptions.LogicException;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.repository.DoctorRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public boolean isExistsByUserIdAndId(UUID doctorUUID, long id) {
        return doctorRepository.existsByUserIdAndId(doctorUUID, id);
    }

    @Override
    public Long getDoctorPersonIdByTalonId(Long talonId) {
        return doctorRepository.getDoctorPersonIdByTalonId(talonId);
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

    @Override
    public Long getDoctorIdByTalonId(Long talonId) {
        return doctorRepository.getDoctorIdByTalonId(talonId);
    }

    @Override
    public boolean existDoctorByUserIdAndDoctorId(UUID userId, long doctorId) {
        return doctorRepository.existsByUserIdAndId(userId, doctorId);
    }
    @Override
    public Doctor findDoctorByUUID(UUID userId) {return doctorRepository.findByUserId(userId);}

    @Override
    public List<Doctor> findAllByUserId(UUID userId) {
        return doctorRepository.findAllByUserId(userId);
    }

    @Override
    public List<Doctor> findAllWithDepartmentsAndOrganizations(UUID userId) {
        return doctorRepository.findAllWithDepartmentsAndOrganizations(userId);
    }

    @Override
    public boolean isDoctorExistsById(Long id) {
        return doctorRepository.isDoctorExistsById(id);
    }

    @Override
    public long getPositionIdByDoctorId(long id) {
        return doctorRepository.getPositionIdByDoctorId(id);
    }

    @Override
    public Long getPersonIdByDoctorId(Long doctorId) {
        return doctorRepository.getPersonIdByDoctorId(doctorId);
    }
}
