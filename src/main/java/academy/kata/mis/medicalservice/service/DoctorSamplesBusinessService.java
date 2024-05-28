package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;

public interface DoctorSamplesBusinessService {

    GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDepAndDoctor(long doctorId,
                                                                                             long diseaseDepId);


}
