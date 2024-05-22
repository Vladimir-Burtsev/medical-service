package academy.kata.mis.medicalservice.feign;

import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.exceptions.FeignRequestException;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static academy.kata.mis.medicalservice.feign.StructureFeignClient.StructureServiceFallbackFactory;

@FeignClient(name = "structure-service", fallbackFactory = StructureServiceFallbackFactory.class)
public interface StructureFeignClient {

    @GetMapping("/internal/structure/organization")
    OrganizationDto getOrganizationById(@RequestParam(name = "organization_id") long organizationId);

    @GetMapping("/internal/structure/positions")
    PositionsNameAndCabinetDto getPositionsNameAndCabinetById(@RequestParam(name = "position_id") long positionId);

    @Component
    class StructureServiceFallbackFactory implements FallbackFactory<FallbackWithFactory> {

        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements StructureFeignClient {

        @Override
        public OrganizationDto getOrganizationById(long organizationId) {
            String responseMessage = """
                    Медицинская организация не существует по переданному id %s
                    """.formatted(organizationId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public PositionsNameAndCabinetDto getPositionsNameAndCabinetById(long positionsId) {
            String responseMessage = """
                    Позиции не существует по переданному id %s
                    """.formatted(positionsId, reason);

            throw new FeignRequestException(responseMessage);
        }
    }
}
