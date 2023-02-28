package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KrakenFuturesStreamingWebsocketMessage {

    private final String event;
    private final String feed;
    private final String[] product_ids;

    public KrakenFuturesStreamingWebsocketMessage(
            @JsonProperty("event") String event,
            @JsonProperty("feed") String feed,
            @JsonProperty("product_ids") String[] product_ids) {
        this.event = event;
        this.feed = feed;
        this.product_ids = product_ids;
    }
}
