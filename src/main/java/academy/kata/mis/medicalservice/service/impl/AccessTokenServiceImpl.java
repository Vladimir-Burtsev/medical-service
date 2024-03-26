package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.service.AccessTokenService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final IMap<String, LocalDateTime> blockAccessMap;

    public AccessTokenServiceImpl(HazelcastInstance hazelcastInstance,
                                  @Value("${hazelcast.cache.block-access-token}") String blockAccessTokenMap) {
        blockAccessMap = hazelcastInstance.getMap(blockAccessTokenMap);
    }

    @Override
    public boolean isBlocked(String accessToken) {
        return blockAccessMap.containsKey(accessToken);
    }
}
