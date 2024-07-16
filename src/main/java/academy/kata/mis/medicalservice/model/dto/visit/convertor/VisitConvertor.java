package academy.kata.mis.medicalservice.model.dto.visit.convertor;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.service.MedicalServiceShortDto;
import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;
import academy.kata.mis.medicalservice.model.dto.visit.VisitShortDto;
import academy.kata.mis.medicalservice.model.entity.Visit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VisitConvertor {
    public VisitShortDto entityToVisitShortDto(Visit visit, DoctorShortDto doctor) {
        return VisitShortDto.builder()
                .visitId(visit.getId())
                .visitTime(visit.getVisitTime())
                .doctor(doctor)
                .build();
    }
    public VisitDto entityToVisitDto(Visit visit, DoctorShortDto doctor, List<MedicalServiceShortDto> medicalServices ) {
        return VisitDto.builder()
                .visitId(visit.getId())
                .visitTime(visit.getVisitTime())
                .doctor(doctor)
                .medicalServices(medicalServices)
                .build();
    }
}
