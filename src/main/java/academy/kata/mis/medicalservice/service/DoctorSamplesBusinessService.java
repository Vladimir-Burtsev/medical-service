package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.GetDiseaseSamplesWithServicesResponse;
import academy.kata.mis.medicalservice.model.entity.DiseaseDep;

public interface DoctorSamplesBusinessService {

    GetDiseaseSamplesWithServicesResponse getDiseaseSamplesWithServicesByDiseaseDep(DiseaseDep diseaseDep);

}
