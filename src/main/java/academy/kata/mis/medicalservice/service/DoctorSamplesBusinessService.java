package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;
import academy.kata.mis.medicalservice.model.entity.Doctor;

import java.util.UUID;

public interface DoctorSamplesBusinessService {

    GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDepAndDoctor(long doctorId,
                                                                                             long diseaseDepId);


}
