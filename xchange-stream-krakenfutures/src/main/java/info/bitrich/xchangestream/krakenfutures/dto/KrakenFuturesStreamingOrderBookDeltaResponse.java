package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class KrakenFuturesStreamingOrderBookDeltaResponse {

    private final String feed;
    private final String product_id;
    private final KrakenFuturesStreamingSide side;
    private final Long seq;
    private final BigDecimal price;
    private final BigDecimal qty;
    private final Date timestamp;

    public KrakenFuturesStreamingOrderBookDeltaResponse(
            @JsonProperty("feed") String feed,
            @JsonProperty("product_id") String product_id,
            @JsonProperty("side") KrakenFuturesStreamingSide side,
            @JsonProperty("seq") Long seq,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("qty") BigDecimal qty,
            @JsonProperty("timestamp") Date timestamp) {
        this.feed = feed;
        this.product_id = product_id;
        this.side = side;
        this.seq = seq;
        this.price = price;
        this.qty = qty;
        this.timestamp = timestamp;
    }

    public enum KrakenFuturesStreamingSide{
        sell,
        buy
    }
}
