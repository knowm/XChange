package org.knowm.xchange.coinfloor.dto.streaming.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.CoinfloorUtils;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorTicker {

  private final int tag;
  private final int errorCode;
  private final CoinfloorCurrency base;
  private final CoinfloorCurrency counter;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal volume;

  public CoinfloorTicker(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode, @JsonProperty("base") int base,
      @JsonProperty("counter") int counter, @JsonProperty("last") int last, @JsonProperty("bid") int bid, @JsonProperty("ask") int ask,
      @JsonProperty("low") int low, @JsonProperty("high") int high, @JsonProperty("volume") int volume) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.base = base == 0 ? CoinfloorCurrency.BTC : CoinfloorUtils.getCurrency(base);
    this.counter = counter == 0 ? CoinfloorCurrency.GBP : CoinfloorUtils.getCurrency(counter);
    this.last = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, last);
    this.bid = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, bid);
    this.ask = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, ask);
    this.low = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, low);
    this.high = CoinfloorUtils.scalePriceToBigDecimal(this.base, this.counter, high);
    this.volume = CoinfloorUtils.scaleToBigDecimal(CoinfloorCurrency.BTC, volume);
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public CoinfloorCurrency getBase() {

    return base;
  }

  public CoinfloorCurrency getCounter() {

    return counter;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  @Override
  public String toString() {

    return "CoinfloorTicker{tag='" + tag + "', errorcode='" + errorCode + "', last='" + last + "', bid='" + bid + "', ask='" + ask + "', low='" + low
        + "', volume='" + volume + "'}";
  }
}
