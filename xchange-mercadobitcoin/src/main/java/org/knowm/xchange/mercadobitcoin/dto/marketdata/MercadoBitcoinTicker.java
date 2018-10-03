package org.knowm.xchange.mercadobitcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Felipe Micaroni Lalli */
public class MercadoBitcoinTicker {

  private final MercadoBitcoinTicker.Ticker ticker;

  public MercadoBitcoinTicker(@JsonProperty("ticker") MercadoBitcoinTicker.Ticker ticker) {

    this.ticker = ticker;
  }

  public Ticker getTicker() {

    return ticker;
  }

  @Override
  public String toString() {

    return "MercadoBitcoinTicker [ticker=" + ticker + "]";
  }

  public static class Ticker {

    private final BigDecimal last;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal vol;
    private final BigDecimal buy;
    private final BigDecimal sell;
    private final long date;

    public Ticker(
        @JsonProperty("last") BigDecimal last,
        @JsonProperty("high") BigDecimal high,
        @JsonProperty("low") BigDecimal low,
        @JsonProperty("vol") BigDecimal vol,
        @JsonProperty("buy") BigDecimal buy,
        @JsonProperty("sell") BigDecimal sell,
        @JsonProperty("date") long date) {

      this.last = last;
      this.high = high;
      this.low = low;
      this.vol = vol;
      this.buy = buy;
      this.sell = sell;
      this.date = date;
    }

    public BigDecimal getLast() {

      return last;
    }

    public BigDecimal getHigh() {

      return high;
    }

    public BigDecimal getLow() {

      return low;
    }

    public BigDecimal getVol() {

      return vol;
    }

    public BigDecimal getBuy() {

      return buy;
    }

    public BigDecimal getSell() {

      return sell;
    }

    public long getDate() {

      return date;
    }

    @Override
    public String toString() {

      return "Ticker ["
          + "last="
          + last
          + ", high="
          + high
          + ", low="
          + low
          + ", vol="
          + vol
          + ", buy="
          + buy
          + ", sell="
          + sell
          + ", date="
          + date
          + ']';
    }
  }
}
