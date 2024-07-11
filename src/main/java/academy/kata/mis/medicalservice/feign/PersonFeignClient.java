package academy.kata.mis.medicalservice.feign;

import academy.kata.mis.medicalservice.exceptions.FeignRequestException;
import academy.kata.mis.medicalservice.model.dto.GetCurrentPatientInformation;
import academy.kata.mis.medicalservice.model.dto.doctor.DoctorShortDto;
import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonFullNameDto;
import academy.kata.mis.medicalservice.model.dto.person.PersonsListDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.UUID;

import static academy.kata.mis.medicalservice.feign.PersonFeignClient.PersonServiceFallbackFactory;

@FeignClient(name = "person-service", fallbackFactory = PersonServiceFallbackFactory.class)
public interface PersonFeignClient {

    @GetMapping("/internal/person/information")
    PersonDto getPersonById(@RequestParam(name = "person_id") long personId);

    @GetMapping("/internal/person/information/short")
    PersonFullNameDto getPersonFullNameDtoById(@RequestParam(name = "person_id") long personId);

    @GetMapping("/internal/person/information/currentpatientinformation")
    GetCurrentPatientInformation getCurrentPersonById(@RequestParam(name = "person_id") long personId);

    @GetMapping("/internal/person/information/currentdoctorinformation")
    DoctorShortDto getDoctorShortDtoByPersonIdAndDoctorId(
            @RequestParam(name = "person_id") long personId,
            @RequestParam(name = "doctor_id") long doctorId);

    @GetMapping("internal/person/information/email")
    String getPersonEmailByUserId(@RequestParam(name = "user_id") UUID userId);

    @GetMapping("internal/person/information/list")
    PersonsListDto getPersonsListByIds(@RequestParam(name = "person_ids") Set<Long> personIds);

    @Component
    class PersonServiceFallbackFactory implements FallbackFactory<FallbackWithFactory> {

        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements PersonFeignClient {

        @Override
        public PersonDto getPersonById(long personId) {
            String responseMessage = """
                    Персона не существует по переданному personId: %s; message: %s
                    """.formatted(personId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public PersonFullNameDto getPersonFullNameDtoById(long personId) {
            String responseMessage = """
                    Персона не существует по переданному personId: %s; message: %s
                    """.formatted(personId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public GetCurrentPatientInformation getCurrentPersonById(long personId) {
            String responseMessage = """
                    Персона не существует по переданному personId: %s; message: %s
                    """.formatted(personId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public DoctorShortDto getDoctorShortDtoByPersonIdAndDoctorId(long personId, long doctorId) {
            String responseMessage = """
                    Персона не существует по переданному personId: %s; message: %s
                    """.formatted(personId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public String getPersonEmailByUserId(UUID userId) {
            String responseMessage = """
                    Не найден email адрес у пользователя с ID = %s, reason = %s
                    """.formatted(userId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public PersonsListDto getPersonsListByIds(Set<Long> personIds) {
            String responseMessage = """
                    Не найдены некоторые personId %s; message: %s
                    """.formatted(personIds, reason);

            throw new FeignRequestException(responseMessage);
        }
    }
}
