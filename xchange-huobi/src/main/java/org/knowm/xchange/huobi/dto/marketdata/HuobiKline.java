package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class HuobiKline {

    private final long id;
    private final long count;
    private final BigDecimal open;
    private final BigDecimal close;
    private final BigDecimal low;
    private final BigDecimal high;
    private final BigDecimal amount;
    private final BigDecimal vol;

    public HuobiKline(
            @JsonProperty("id") long id,
            @JsonProperty("count") long count,
            @JsonProperty("open") BigDecimal open,
            @JsonProperty("close") BigDecimal close,
            @JsonProperty("low") BigDecimal low,
            @JsonProperty("high") BigDecimal high,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("vol") BigDecimal vol) {
        this.id = id;
        this.count = count;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.amount = amount;
        this.vol = vol;
    }

    @Override
    public String toString() {
        return String.format(
                "[id = %d, open = %f, close = %f, low = %f, high = %f, amount = %f, vol = %f, count = %d]",
                getId(),
                getOpen(),
                getClose(),
                getLow(),
                getHigh(),
                getAmount(),
                getVol(),
                getCount()
        );
    }

    public long getId() {
        return id;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getVol() {
        return vol;
    }
}
