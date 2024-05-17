package academy.kata.mis.medicalservice.service;

import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;

import java.util.Map;
import java.util.Set;

public interface PersonService {
    PersonFullNameDto getPatientShortDtoByPersonId(long patientId);
    Map<Long, PersonFullNameDto> getPersonsFullName(Set<Long> doctorsId);
}
