package academy.kata.mis.medicalservice.feign;

import academy.kata.mis.medicalservice.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.exceptions.FeignRequestException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static academy.kata.mis.medicalservice.feign.OrganizationFeignClient.OrganizationServiceFallbackFactory;

@FeignClient(name = "structure-service", fallbackFactory = OrganizationServiceFallbackFactory.class)
public interface OrganizationFeignClient {

    @GetMapping("/internal/structure/organization")
    OrganizationDto getOrganizationById(@RequestParam(name = "organization_id") long organizationId);

    @Component
    class OrganizationServiceFallbackFactory implements FallbackFactory<FallbackWithFactory> {

        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements OrganizationFeignClient {

        @Override
        public OrganizationDto getOrganizationById(long departmentId) {
            String responseMessage = """
                    Медицинская организация не существует по переданному id
                    """.formatted(departmentId, reason);

            throw new FeignRequestException(responseMessage);
        }
    }
}
