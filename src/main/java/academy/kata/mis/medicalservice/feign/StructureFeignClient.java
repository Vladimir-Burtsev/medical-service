package academy.kata.mis.medicalservice.feign;

import academy.kata.mis.medicalservice.exceptions.FeignRequestException;
import academy.kata.mis.medicalservice.model.dto.PositionDto;
import academy.kata.mis.medicalservice.model.dto.department_organization.DepartmentAndOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.feign.DepartmentDto;
import academy.kata.mis.medicalservice.model.dto.feign.OrganizationDto;
import academy.kata.mis.medicalservice.model.dto.positions.PositionsNameAndCabinetDto;
import academy.kata.mis.medicalservice.model.dto.positions.RepPositionsDepartmentOrganizationDto;
import academy.kata.mis.medicalservice.model.dto.department_organization_position_cabinet.DepartmentOrganizationPositionCabinetNameDto;
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

    @GetMapping("/internal/structure/organization/departmentname")
    DepartmentDto getDepartmentById(@RequestParam(name = "department_id") long departmentId);

    @GetMapping("/internal/structure/positions")
    PositionsNameAndCabinetDto getPositionsNameAndCabinetById(@RequestParam(name = "position_id") long positionId);

    @GetMapping("/internal/structure/organization/positionname")
    PositionDto getPositionNameById(
            @RequestParam(name = "position_id") long positionId);

    @GetMapping("/internal/structure/department-organization")
    DepartmentAndOrganizationDto getDepartmentAndOrganizationName(
            @RequestParam(name = "department_id") Long departmentId);

    @GetMapping("/internal/structure/organization/reppositionsdepartmentorganization")
    RepPositionsDepartmentOrganizationDto getRepPositionsDepartmentOrganizationByPositionId(
            @RequestParam(name = "position_id") long positionId);

    @GetMapping("/internal/structure/department-organization-position-cabinet")
    DepartmentOrganizationPositionCabinetNameDto getDepartmentOrganizationPositionCabinetNameDto(
            @RequestParam(name = "position_id") long positionId);

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
        public RepPositionsDepartmentOrganizationDto getRepPositionsDepartmentOrganizationByPositionId(
                long positionId) {
            String responseMessage = """
                    Позиция не существует по переданному id %s
                    """.formatted(positionId, reason);
            throw new FeignRequestException(responseMessage);
        }

        @Override
        public DepartmentDto getDepartmentById(long departmentId) {
            String responseMessage = """
                    Департамент не существует по переданному id = %s не найден
                    """.formatted(departmentId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public PositionsNameAndCabinetDto getPositionsNameAndCabinetById(long positionsId) {
            String responseMessage = """
                    Позиции не существует по переданному id %s
                    """.formatted(positionsId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public PositionDto getPositionNameById(long positionId) {
            String responseMessage = """
                    Позиция не существует по переданному id %s
                    """.formatted(positionId, reason);

            throw new FeignRequestException(responseMessage);
        }

        @Override
        public DepartmentAndOrganizationDto getDepartmentAndOrganizationName(Long departmentId) {
            String responseMassage = """
                    Департамент с указанным Id = %s не найден.
                    """.formatted(departmentId, reason);

            throw new FeignRequestException(responseMassage);
        }

        @Override
        public DepartmentOrganizationPositionCabinetNameDto getDepartmentOrganizationPositionCabinetNameDto(long positionId) {
            String responseMassage = """
                    Позиция с указанным Id = %s не найдена.
                    """.formatted(positionId, reason);

            throw new FeignRequestException(responseMassage);
        }
    }
}
