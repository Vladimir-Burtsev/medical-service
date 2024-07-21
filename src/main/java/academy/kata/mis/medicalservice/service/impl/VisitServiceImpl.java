package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.doctor.convertor.DoctorConvertor;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import academy.kata.mis.medicalservice.model.dto.service.convertor.MedicalServiceConvertor;
import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.model.dto.visit.convertor.VisitConvertor;
import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
import academy.kata.mis.medicalservice.repository.VisitRepository;
import academy.kata.mis.medicalservice.service.DoctorService;
import academy.kata.mis.medicalservice.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;
    private final DoctorService doctorService;
    private final VisitConvertor visitConvertor;
    private final DoctorConvertor doctorConvertor;
    private final MedicalServiceConvertor medicalServiceConvertor;

    @Override
    public Visit createPatientVisit(Doctor doctor, Appeal appeal) {
        return Visit.builder()
                .visitTime(LocalDate.now().atStartOfDay())
                .doctor(doctor)
                .appeal(appeal)
                .build();
    }

    @Override
    @Transactional
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit findVisitById(long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public VisitDto getVisitInfo(long visitId) {
        Visit visit = findVisitById(visitId);
        Doctor doctor = doctorService.findDoctorByUUID(visit.getDoctor().getUserId());
        DoctorShortDto doctorShortDto = doctorConvertor
                .convertDoctorToDoctorShortDto(doctor);
        List<MedicalServiceShortDto> medicalService = medicalServiceConvertor
                .convertMedicalServiceToMedicalServiceShortDto(visit.getMedicalServicesDep());
        return visitConvertor.entityToVisitDto(visit, doctorShortDto, medicalService);
    }
}
