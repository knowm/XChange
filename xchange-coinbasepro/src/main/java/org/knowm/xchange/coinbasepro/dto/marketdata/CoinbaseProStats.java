package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;
import java.util.Map;

public final class CoinbaseProStats {
  private CurrencyPair currencyPair;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal last;

  public CoinbaseProStats(@JsonProperty("stats_24hour") Map<String, BigDecimal> stats24Hour) {
    this.open = stats24Hour.get("open");
    this.high = stats24Hour.get("high");
    this.low = stats24Hour.get("low");
    this.volume = stats24Hour.get("volume");
    this.last = stats24Hour.get("last");
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
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

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getLast() {
    return last;
  }
}
