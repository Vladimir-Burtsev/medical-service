package academy.kata.mis.medicalservice.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;

@Configuration
public class HazelcastConfig {

    @Value("${hazelcast.cache.block-access-token}")
    private String blockAccessTokenMap;

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "hazelcast", value = "embedded", havingValue = "false")
    public HazelcastInstance hazelcastClientInstance() {
        var clientConfig = new ClientConfig();
        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "hazelcast", value = "embedded", havingValue = "true")
    public HazelcastInstance hazelcastInstance() {
        var config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
        config.getJetConfig().setEnabled(true);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean("accessTokenMap")
    @Primary
    public IMap<String, LocalDateTime> getBlockAccessTokenMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap(blockAccessTokenMap);
    }

}
