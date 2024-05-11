package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.entity.Doctor;

public interface DoctorService {

    Doctor findDoctorById(long doctorId);

    boolean isExistById(long id);

}
