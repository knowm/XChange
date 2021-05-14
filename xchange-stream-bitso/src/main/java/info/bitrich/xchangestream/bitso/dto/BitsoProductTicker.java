package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BitsoProductTicker {

    private final String tradeId;
    private final BigDecimal price;
    private final BigDecimal size;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final BigDecimal volume;
    private final String time;

    public BitsoProductTicker(@JsonProperty("trade_id") String tradeId, @JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size, @JsonProperty("bid") BigDecimal bid, @JsonProperty("ask") BigDecimal ask, @JsonProperty("volume") BigDecimal volume, @JsonProperty("time") String time) {
        this.tradeId = tradeId;
        this.price = price;
        this.size = size;
        this.bid = bid;
        this.ask = ask;
        this.volume = volume;
        this.time = time;
    }

    public String getTradeId() {
        return this.tradeId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getSize() {
        return this.size;
    }

    public BigDecimal getBid() {
        return this.bid;
    }

    public BigDecimal getAsk() {
        return this.ask;
    }

    public BigDecimal getVolume() {
        return this.volume;
    }

    public String getTime() {
        return this.time;
    }

    public String toString() {
        return "BitsoProductTicker [tradeId=" + this.tradeId + ", price=" + this.price + ", size=" + this.size + ", bid=" + this.bid + ", ask=" + this.ask + ", volume=" + this.volume + ", time=" + this.time + "]";
    }
}
