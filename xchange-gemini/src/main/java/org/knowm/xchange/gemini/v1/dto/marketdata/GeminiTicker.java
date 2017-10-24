package org.knowm.xchange.gemini.v1.dto.marketdata;

import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiTicker {

  public static class Volume {
    private final Map<String, Object> valueMap;

    public Volume(Map<String, Object> valueMap) {
      this.valueMap = valueMap;
    }

    public long getTimestampMS() {
      return (long) valueMap.get("timestamp");
    }

    public BigDecimal getBaseVolume(CurrencyPair currencyPair) {
      return new BigDecimal((String) valueMap.get(currencyPair.base.toString()));
    }

    public BigDecimal getCounterVolume(CurrencyPair currencyPair) {
      return new BigDecimal((String) valueMap.get(currencyPair.counter.toString()));
    }
  }

  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal last;
  private final Volume volume;

  /**
   * @param bid
   * @param ask
   * @param last
   * @param volume
   */
  public GeminiTicker(@JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("volume") Map<String, Object> volume) {

    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = new Volume(volume);
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getLast() {

    return last;
  }

  public Volume getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return "GeminiTicker [bid=" + bid + ", ask=" + ask + ", last=" + last + ", volume="
        + volume + "]";
  }

}

