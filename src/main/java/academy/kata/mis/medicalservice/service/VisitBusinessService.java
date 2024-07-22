package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.visit.VisitDto;

public interface VisitBusinessService {
    VisitDto getVisitInfo(long visitId);
}
