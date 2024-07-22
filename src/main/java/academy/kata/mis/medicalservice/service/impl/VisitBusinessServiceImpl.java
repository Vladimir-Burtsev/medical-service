package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import academy.kata.mis.medicalservice.model.dto.service.convertor.MedicalServiceConvertor;
import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.model.dto.visit.convertor.VisitConvertor;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
import academy.kata.mis.medicalservice.repository.VisitRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.VisitBusinessService;
import academy.kata.mis.medicalservice.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class VisitBusinessServiceImpl implements VisitBusinessService {
    private final VisitService visitService;
    private final DoctorService doctorService;
    private final DoctorConvertor doctorConvertor;
    private final MedicalServiceConvertor medicalServiceConvertor;
    private final VisitConvertor visitConvertor;

    @Override
    public VisitDto getVisitInfo(long visitId) {
        Visit visit = visitService.findVisitById(visitId);
        Doctor doctor = doctorService.findDoctorByUUID(visit.getDoctor().getUserId());
        DoctorShortDto doctorShortDto = doctorConvertor
                .convertDoctorToDoctorShortDto(doctor);
        List<MedicalServiceShortDto> medicalService = medicalServiceConvertor
                .convertMedicalServiceToMedicalServiceShortDto(visit.getMedicalServicesDep());
        return visitConvertor.entityToVisitDto(visit, doctorShortDto, medicalService);
    }
}
