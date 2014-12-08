package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.DateUtils;

/**
 * <p>
 * A class encapsulating the most basic information a "Ticker" should contain.
 * </p>
 * <p>
 * A ticker contains data representing the latest trade.
 * </p>
 */
public final class Ticker {

  private final CurrencyPair currencyPair;
  private final BigDecimal last;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final Date timestamp;

  /**
   * Constructor
   * 
   * @param currencyPair The tradable identifier (e.g. BTC in BTC/USD)
   * @param last
   * @param bid
   * @param ask
   * @param high
   * @param low
   * @param volume 24h volume
   * @param timestamp
   */
  private Ticker(CurrencyPair currencyPair, BigDecimal last, BigDecimal bid, BigDecimal ask, BigDecimal high, BigDecimal low, BigDecimal volume, Date timestamp) {

    this.currencyPair = currencyPair;
    this.last = last;
    this.bid = bid;
    this.ask = ask;
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
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

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  @Override
  public String toString() {

    return "Ticker [currencyPair=" + currencyPair + ", last=" + last + ", bid=" + bid + ", ask=" + ask + ", high=" + high + ", low=" + low + ", volume=" + volume + ", timestamp=" + DateUtils.toMillisNullSafe(timestamp) + "]";
  }

  /**
   * <p>
   * Builder to provide the following to {@link Ticker}:
   * </p>
   * <ul>
   * <li>Provision of fluent chained construction interface</li>
   * </ul>
   *  
   */
  public static class Builder {

    private CurrencyPair currencyPair;
    private BigDecimal last;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal volume;
    private Date timestamp;

    // Prevent repeat builds
    private boolean isBuilt = false;

    public Ticker build() {

      validateState();

      Ticker ticker = new Ticker(currencyPair, last, bid, ask, high, low, volume, timestamp);

      isBuilt = true;

      return ticker;
    }

    private void validateState() {

      if (isBuilt) {
        throw new IllegalStateException("The entity has been built");
      }
    }

    public Builder currencyPair(CurrencyPair currencyPair) {

      this.currencyPair = currencyPair;
      return this;
    }

    public Builder last(BigDecimal last) {

      this.last = last;
      return this;
    }

    public Builder bid(BigDecimal bid) {

      this.bid = bid;
      return this;
    }

    public Builder ask(BigDecimal ask) {

      this.ask = ask;
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

    public Builder volume(BigDecimal volume) {

      this.volume = volume;
      return this;
    }

    public Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

  }
}