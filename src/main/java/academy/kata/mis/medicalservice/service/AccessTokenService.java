package academy.kata.mis.medicalservice.service;

public interface AccessTokenService {
    boolean isBlocked(String accessToken);
}
