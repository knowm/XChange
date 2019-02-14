package info.bitrich.xchangestream.bitstamp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class BitstampOrderBook {
    private final long timestamp;
    private final List<List<BigDecimal>> bids;
    private final List<List<BigDecimal>> asks;

    public BitstampOrderBook(@JsonProperty("timestamp") long timestamp, @JsonProperty("bids") List<List<BigDecimal>> bids, @JsonProperty("asks") List<List<BigDecimal>> asks) {
        this.timestamp = timestamp;
        this.bids = bids;
        this.asks = asks;
    }

    public List<List<BigDecimal>> getBids() {
        return bids;
    }

    public List<List<BigDecimal>> getAsks() {
        return asks;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
