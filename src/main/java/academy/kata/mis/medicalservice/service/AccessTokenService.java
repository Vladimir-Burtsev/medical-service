package academy.kata.mis.medicalservice.service;

public interface AccessTokenService {
    void addToBlock(String accessToken);
    boolean isBlocked(String accessToken);
}
