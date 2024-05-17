package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.feign.PersonFeignClient;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonFeignClient personFeignClient;

    @Override
    public PersonFullNameDto getPatientShortDtoByPersonId(long personId) {
        PersonFullNameDto personFullNameDto = personFeignClient.getPersonFullNameDtoById(personId);
        existPersons(personFullNameDto, personId);
        return personFullNameDto;
    }

    @Override
    public Map<Long, PersonFullNameDto> getPersonsFullName(Set<Long> personsId) {
        Map<Long, PersonFullNameDto> personsFullNameById = new HashMap<>();
        personsId.forEach(id -> personsFullNameById.put(id, getPatientShortDtoByPersonId(id)));
        return personsFullNameById;
    }

    private void existPersons(PersonFullNameDto person, long personId) {
        if (person == null) {
            throw new NoSuchElementException("Персоны с ID: " + personId + " - не существует");
        }
    }
}
