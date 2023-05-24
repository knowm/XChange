package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenFuturesStreamingOrderBookSnapshotResponse {

    private final String feed;
    private final String product_id;
    private final Date timestamp;
    private final Long seq;
    private final String tickSize;
    private final List<KrakenFuturesSnapShotOrder> bids;
    private final List<KrakenFuturesSnapShotOrder> asks;

    public KrakenFuturesStreamingOrderBookSnapshotResponse(
            @JsonProperty("feed") String feed,
            @JsonProperty("product_id") String product_id,
            @JsonProperty("timestamp") Date timestamp,
            @JsonProperty("seq") Long seq,
            @JsonProperty("tickSize") String tickSize,
            @JsonProperty("bids") List<KrakenFuturesSnapShotOrder> bids,
            @JsonProperty("asks") List<KrakenFuturesSnapShotOrder> asks) {
        this.feed = feed;
        this.product_id = product_id;
        this.timestamp = timestamp;
        this.seq = seq;
        this.tickSize = tickSize;
        this.bids = bids;
        this.asks = asks;
    }

    @Getter
    public static class KrakenFuturesSnapShotOrder {
        private final BigDecimal price;
        private final BigDecimal quantity;

        public KrakenFuturesSnapShotOrder(
                @JsonProperty("price") BigDecimal price,
                @JsonProperty("qty") BigDecimal quantity) {
            this.price = price;
            this.quantity = quantity;
        }
    }
}
