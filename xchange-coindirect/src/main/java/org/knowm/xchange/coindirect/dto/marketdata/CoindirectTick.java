package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CoindirectTick {
    public final long time;
    public final BigDecimal price;
    public final BigDecimal volume;

    public CoindirectTick(@JsonProperty("time") long time, @JsonProperty("price") BigDecimal price, @JsonProperty("volume") BigDecimal volume) {
        this.time = time;
        this.price = price;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "CoindirectTick{" +
                "time=" + time +
                ", price=" + price +
                ", volume=" + volume +
                '}';
    }
}
