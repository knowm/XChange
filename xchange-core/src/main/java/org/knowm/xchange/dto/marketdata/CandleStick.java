package org.knowm.xchange.dto.marketdata;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data object representing a CandleStick
 */
@JsonDeserialize(builder = CandleStick.Builder.class)
public class CandleStick {

    private final Date date;
    private final BigDecimal open;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal volume;
    private final BigDecimal currencyVolume;

    public CandleStick(Date date,BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal volume, BigDecimal currencyVolume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.currencyVolume = currencyVolume;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getCurrencyVolume() {
        return currencyVolume;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        protected Date date;
        protected BigDecimal open;
        protected BigDecimal high;
        protected BigDecimal low;
        protected BigDecimal close;
        protected BigDecimal volume;
        protected BigDecimal currencyVolume;


        public static Builder from(CandleStick candleStick) {
            return new Builder()
                    .date(candleStick.getDate())
                    .open(candleStick.getOpen())
                    .high(candleStick.getHigh())
                    .low(candleStick.getLow())
                    .close(candleStick.getClose())
                    .volume(candleStick.getVolume())
                    .currencyVolume(candleStick.getCurrencyVolume());
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder open(BigDecimal open) {
            this.open = open;
            return this;
        }

        public Builder high(BigDecimal high) {
            this.high = high;
            return this;
        }

        public Builder low(BigDecimal low) {
            this.low = low;
            return this;
        }

        public Builder close(BigDecimal close) {
            this.close = close;
            return this;
        }

        public Builder volume(BigDecimal volume) {
            this.volume = volume;
            return this;
        }

        public Builder currencyVolume(BigDecimal currencyVolume) {
            this.currencyVolume = currencyVolume;
            return this;
        }

        public CandleStick build() {
            return new CandleStick(date, open, high, low, close, volume, currencyVolume);
        }
    }
}
