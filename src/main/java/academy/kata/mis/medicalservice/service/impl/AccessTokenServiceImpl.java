package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.AccessTokenService;
import academy.kata.mis.medicalservice.util.WeakConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final WeakConcurrentHashMap<String, Object> blockedTokenCache =
            new WeakConcurrentHashMap<>(300_000);

    @Override
    public void addToBlock(String accessToken) {
        blockedTokenCache.put(accessToken.substring(7), new Object());
    }

    @Override
    public boolean isBlocked(String accessToken) {
        return blockedTokenCache.containsKey(accessToken);
    }
}
