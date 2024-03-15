package academy.kata.mis.medicalservice.model.dto.auth;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record JwtAuthenticationDto(UUID user, List<String> claims, Date createDate, Date expirationDate) {}
