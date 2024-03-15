package academy.kata.mis.medicalservice.model.dto.kafka.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BlockedAccessToken {
    private String accessToken;

    public BlockedAccessToken(
            @JsonProperty("access_token") String accessToken) {
        this.accessToken = accessToken;
    }
}
