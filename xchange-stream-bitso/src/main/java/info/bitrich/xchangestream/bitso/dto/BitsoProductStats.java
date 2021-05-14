package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BitsoProductStats {
    private final BigDecimal open;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal volume;

    public BitsoProductStats(@JsonProperty("open") BigDecimal open, @JsonProperty("high") BigDecimal high, @JsonProperty("low") BigDecimal low, @JsonProperty("volume") BigDecimal volume) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public BigDecimal getOpen() {
        return this.open;
    }

    public BigDecimal getHigh() {
        return this.high;
    }

    public BigDecimal getLow() {
        return this.low;
    }

    public BigDecimal getVolume() {
        return this.volume;
    }
}
