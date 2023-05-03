package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KrakenFuturesStreamingChallengeResponse {

    private final String event;
    private final String message;

    public KrakenFuturesStreamingChallengeResponse(
            @JsonProperty("event") String event,
            @JsonProperty("message") String message) {
        this.event = event;
        this.message = message;
    }
}
