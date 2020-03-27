package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.Assert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * A class encapsulating the information a "Candle" can contain. Some fields can be empty if not
 * provided by the exchange.
 *
 * <p>A Candle contains data representing the trading range for a timeperiod
 */
@JsonDeserialize(builder = Candle.Builder.class)
public class Candle implements Serializable {
  private static final long serialVersionUID = 1L;

  private final CurrencyPair currencyPair;
  private final BigDecimal open;
  private final BigDecimal close;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal vwap;
  private final Date openTime, closeTime;
  private final long granularity;

  /** the timestamp of the ticker according to the exchange's server, null if not provided */
  private final Date timestamp;
  /**
   * Constructor
   *
   * @param currencyPair The tradable identifier
   * @param open open price
   * @param close Last price
   * @param high High price
   * @param low Low price
   * @param vwap Volume Weighted Average Price
   * @param volume 24h volume in base currency
   * @param openTime - the open time for the candle
   * @param closeTime - the close time for the candle
   * @param granularity - the duration of the candle, in seconds
   * @param timestamp - the timestamp of the candle according to the exchange's server, null if not
   *     provided
   */
  private Candle(
      CurrencyPair currencyPair,
      BigDecimal open,
      BigDecimal close,
      BigDecimal high,
      BigDecimal low,
      BigDecimal vwap,
      BigDecimal volume,
      Date openTime,
      Date closeTime,
      long granularity,
      Date timestamp) {
    this.currencyPair = currencyPair;
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
    this.volume = volume;
    this.openTime = openTime;
    this.closeTime = closeTime;
    this.granularity = granularity;
    this.timestamp = timestamp;
  }

  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getClose() {
    return close;
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

  public BigDecimal getVwap() {
    return vwap;
  }

  public Date getOpenTime() {
    return openTime;
  }

  public Date getCloseTime() {
    return closeTime;
  }

  public long getGranularity() {
    return granularity;
  }

  public Date getTimestamp() {
    return timestamp;
  }
  /**
   * Builder to provide the following to {@link Candle}:
   *
   * <ul>
   *   <li>Provision of fluent chained construction interface
   * </ul>
   */
  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

    private CurrencyPair currencyPair;
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal vwap;
    private BigDecimal volume;
    private Date openTime, closeTime;
    private long granularity;
    private Date timestamp;

    // Prevent repeat builds
    private boolean isBuilt = false;

    public Candle build() {

      validateState();

      Candle candle =
          new Candle(
              currencyPair,
              open,
              close,
              high,
              low,
              vwap,
              volume,
              openTime,
              closeTime,
              granularity,
              timestamp);

      isBuilt = true;

      return candle;
    }

    private void validateState() {

      if (isBuilt) {
        throw new IllegalStateException("The entity has been built");
      }
    }

    public Candle.Builder currencyPair(CurrencyPair currencyPair) {
      Assert.notNull(currencyPair, "Null currencyPair");
      this.currencyPair = currencyPair;
      return this;
    }

    public Candle.Builder open(BigDecimal open) {

      this.open = open;
      return this;
    }

    public Candle.Builder close(BigDecimal last) {

      this.close = last;
      return this;
    }

    public Candle.Builder high(BigDecimal high) {

      this.high = high;
      return this;
    }

    public Candle.Builder low(BigDecimal low) {

      this.low = low;
      return this;
    }

    public Candle.Builder vwap(BigDecimal vwap) {

      this.vwap = vwap;
      return this;
    }

    public Candle.Builder volume(BigDecimal volume) {

      this.volume = volume;
      return this;
    }

    public Candle.Builder openTime(Date openTime) {

      this.openTime = openTime;
      return this;
    }

    public Candle.Builder closeTime(Date closeTime) {

      this.closeTime = closeTime;
      return this;
    }

    public Candle.Builder granularity(long granularity) {

      this.granularity = granularity;
      return this;
    }

    public Candle.Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }
  }
}
