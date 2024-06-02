package academy.kata.mis.medicalservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;


@Getter
@RequiredArgsConstructor
public enum DiseaseOrder {
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name")),
    NAME_DESC(Sort.by(Sort.Direction.DESC, "name")),
    IDENTIFIER_ASC(Sort.by(Sort.Direction.ASC, "identifier")),
    IDENTIFIER_DESC(Sort.by(Sort.Direction.DESC, "identifier"));

    private final Sort orderBy;
}
