package academy.kata.mis.medicalservice.model.dto.visit.convertor;

import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.visit.VisitShortDto;
import academy.kata.mis.medicalservice.model.entity.Doctor;
import academy.kata.mis.medicalservice.model.entity.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitConvertor {
    public VisitShortDto entityToVisitShortDto(Visit visit, DoctorShortDto doctor) {
        return VisitShortDto.builder()
                .visitId(visit.getId())
                .visitTime(visit.getVisitTime())
                .doctor(doctor)
                .build();
    }
}
