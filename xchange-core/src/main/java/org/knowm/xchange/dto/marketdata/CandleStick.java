package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Data object representing a CandleStick
 */
@ToString
@Getter
@JsonDeserialize(builder = CandleStick.Builder.class)
@AllArgsConstructor
public class CandleStick {

  private final BigDecimal open;
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal volume;
  private final BigDecimal quotaVolume;
  private final BigDecimal vwap; // these 5 fields can be null if not provided by the exchange
  private final BigDecimal bid;
  private final BigDecimal bidSize;
  private final BigDecimal ask;
  private final BigDecimal askSize;
  private final Instant timestamp;
  private final boolean completed;

  public CandleStick(
      Instant timestamp,
          BigDecimal open,
          BigDecimal last,
          BigDecimal high,
          BigDecimal low,
          BigDecimal close,
          BigDecimal volume,
          BigDecimal quotaVolume,
          BigDecimal vwap,
          BigDecimal bid,
          BigDecimal bidSize,
          BigDecimal ask,
          BigDecimal askSize,
          boolean completed) {
    this.timestamp = timestamp;
    this.open = open;
    this.last = last;
    this.high = high;
    this.low = low;
    this.close = close;
    this.volume = volume;
    this.quotaVolume = quotaVolume;
    this.vwap = vwap;
    this.bid = bid;
    this.bidSize = bidSize;
    this.ask = ask;
    this.askSize = askSize;
    this.completed = completed;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
    private Instant timestamp;
    private BigDecimal open;
    private BigDecimal last;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private BigDecimal quotaVolume;
    private BigDecimal vwap;
    private BigDecimal bid;
    private BigDecimal bidSize;
    private BigDecimal ask;
    private BigDecimal askSize;
    private boolean completed;

    public static Builder from(CandleStick candleStick) {
      return new Builder()
              .timestamp(candleStick.getTimestamp())
              .open(candleStick.getOpen())
              .last(candleStick.getLast())
              .high(candleStick.getHigh())
              .low(candleStick.getLow())
              .close(candleStick.getClose())
              .volume(candleStick.getVolume())
              .quotaVolume(candleStick.getQuotaVolume())
              .vwap(candleStick.getVwap())
              .bid(candleStick.getBid())
              .bidSize(candleStick.getBidSize())
              .ask(candleStick.getAsk())
              .askSize(candleStick.getAskSize())
              .completed(candleStick.isCompleted());
    }

    public Builder timestamp(Instant timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder open(BigDecimal open) {
      this.open = open;
      return this;
    }

    public Builder last(BigDecimal last) {
      this.last = last;
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

    public Builder quotaVolume(BigDecimal quotaVolume) {
      this.quotaVolume = quotaVolume;
      return this;
    }

    public Builder vwap(BigDecimal vwap) {
      this.vwap = vwap;
      return this;
    }

    public Builder bid(BigDecimal bid) {
      this.bid = bid;
      return this;
    }

    public Builder bidSize(BigDecimal bidSize) {
      this.bidSize = bidSize;
      return this;
    }

    public Builder ask(BigDecimal ask) {
      this.ask = ask;
      return this;
    }

    public Builder askSize(BigDecimal askSize) {
      this.askSize = askSize;
      return this;
    }

    public Builder completed(boolean completed) {
      this.completed = completed;
      return this;
    }

    public CandleStick build() {
      return new CandleStick(
              timestamp,
              open,
              last,
              high,
              low,
              close,
              volume,
              quotaVolume,
              vwap,
              bid,
              bidSize,
              ask,
              askSize,
              completed);
    }
  }
}
