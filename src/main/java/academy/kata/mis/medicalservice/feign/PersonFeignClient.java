package academy.kata.mis.medicalservice.feign;

import academy.kata.mis.medicalservice.model.dto.feign.PersonDto;
import academy.kata.mis.medicalservice.exceptions.FeignRequestException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static academy.kata.mis.medicalservice.feign.PersonFeignClient.PersonServiceFallbackFactory;

@FeignClient(name = "person-service", fallbackFactory = PersonServiceFallbackFactory.class)
public interface PersonFeignClient {

    @GetMapping("/internal/person/information")
    PersonDto getPersonById(@RequestParam(name = "person_id") long personId);

    @GetMapping("internal/person/information/contact")
    String getPersonContactByUserId(@RequestParam(name = "user_id") UUID userId);

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
        public String getPersonContactByUserId(UUID userId) {
            String responseMessage = """
                    Контакт не найден для переданного userId: %s; message: %s
                    """.formatted(userId, reason);

            throw new FeignRequestException(responseMessage);
        }
    }
}
