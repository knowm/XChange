package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KrakenFuturesStreamingChallengeRequest {

    @JsonProperty("event")
    private final String event = "challenge";
    private final String api_key;

    public KrakenFuturesStreamingChallengeRequest(
            @JsonProperty("api_key") String api_key
    ) {
        this.api_key = api_key;
    }
}
