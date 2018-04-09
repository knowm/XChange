package org.knowm.xchange.bitcoinaverage.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Data object representing Ticker from BitcoinAverage */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class BitcoinAverageTicker {

  private final BigDecimal last;
  private final BigDecimal ask;
  private final BigDecimal bid;
  private final BigDecimal volume_percent;
  private final BigDecimal volume;
  private final String timestamp;

  /**
   * Constructor
   *
   * @param bid
   * @param ask
   * @param volume
   * @param last
   * @param timestamp
   */
  public BitcoinAverageTicker(
      @JsonProperty("ask") BigDecimal ask,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("volume_btc") BigDecimal volume,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("volume_percent") BigDecimal volume_percent,
      @JsonProperty("timestamp") String timestamp) {

    this.ask = ask;
    this.bid = bid;
    this.volume = volume;
    this.last = last;
    this.volume_percent = volume_percent;
    this.timestamp = timestamp;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getVolumePercent() {

    return volume_percent;
  }

  public Date getTimestamp() {

    try {
      // Parse the timestamp into a Date object
      return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.getDefault())
          .parse(timestamp);
    } catch (IllegalArgumentException | ParseException e) {
      // Return current Date
      return new Date();
    }
  }

  @Override
  public String toString() {

    return "BitcoinAverageTicker [last="
        + last
        + ", ask="
        + ask
        + ", bid="
        + bid
        + ", volume="
        + volume
        + ", volume_percent="
        + volume_percent
        + ", timestamp="
        + timestamp
        + "]";
  }
}
