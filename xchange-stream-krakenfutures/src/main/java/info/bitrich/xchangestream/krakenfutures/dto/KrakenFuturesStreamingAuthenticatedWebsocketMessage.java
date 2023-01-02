package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenFuturesStreamingAuthenticatedWebsocketMessage extends KrakenFuturesStreamingWebsocketMessage {

    private final String api_key;
    private final String original_challenge;
    private final String signed_challenge;

    public KrakenFuturesStreamingAuthenticatedWebsocketMessage(
            @JsonProperty("event") String event,
            @JsonProperty("feed") String feed,
            @JsonProperty("product_ids") String[] product_ids,
            @JsonProperty("api_key") String api_key,
            @JsonProperty("original_challenge") String original_challenge,
            @JsonProperty("signed_challenge") String signed_challenge) {
        super(event, feed, product_ids);
        this.api_key = api_key;
        this.original_challenge = original_challenge;
        this.signed_challenge = signed_challenge;
    }
}
