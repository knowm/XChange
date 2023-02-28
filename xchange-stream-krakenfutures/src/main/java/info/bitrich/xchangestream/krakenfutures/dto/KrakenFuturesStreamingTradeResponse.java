package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
public class KrakenFuturesStreamingTradeResponse {

    private final String feed;
    private final String product_id;
    private final String uid;
    private final KrakenFuturesStreamingOrderBookDeltaResponse.KrakenFuturesStreamingSide side;
    private final KrakenFuturesStreamingType type;
    private final Long seq;
    private final Date time;
    private final BigDecimal price;
    private final BigDecimal qty;

    public KrakenFuturesStreamingTradeResponse(
            @JsonProperty("feed") String feed,
            @JsonProperty("product_id") String product_id,
            @JsonProperty("uid") String uid,
            @JsonProperty("side") KrakenFuturesStreamingOrderBookDeltaResponse.KrakenFuturesStreamingSide side,
            @JsonProperty("type") KrakenFuturesStreamingType type,
            @JsonProperty("seq") Long seq,
            @JsonProperty("time") Date time,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("qyt") BigDecimal qty
    ) {
        this.feed = feed;
        this.product_id = product_id;
        this.uid = uid;
        this.side = side;
        this.type = type;
        this.seq = seq;
        this.time = time;
        this.price = price;
        this.qty = qty;
    }

    public enum KrakenFuturesStreamingType {
        fill,
        liquidation,
        termination
    }
}
