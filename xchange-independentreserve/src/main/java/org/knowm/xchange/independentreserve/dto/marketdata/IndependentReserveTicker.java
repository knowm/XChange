package org.knowm.xchange.independentreserve.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** @author Stuart Low */
public final class IndependentReserveTicker {
  private final BigDecimal last;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final BigDecimal bid;
  private final BigDecimal ask;
  private Date timestamp;

  /**
   * Constructor
   *
   * @param last
   * @param high
   * @param low
   * @param vwap
   * @param volume
   * @param bid
   * @param ask
   */
  public IndependentReserveTicker(
      @JsonProperty("LastPrice") BigDecimal last,
      @JsonProperty("DayHighestPrice") BigDecimal high,
      @JsonProperty("DayLowestPrice") BigDecimal low,
      @JsonProperty("DayAvgPrice") BigDecimal vwap,
      @JsonProperty("DayVolumeXbt") BigDecimal volume,
      @JsonProperty("CurrentHighestBidPrice") BigDecimal bid,
      @JsonProperty("CurrentLowestOfferPrice") BigDecimal ask,
      @JsonProperty("CreatedTimestampUtc") String timestamp) {

    // @JsonFormat(pattern="") @JsonDeserialize(using =
    // TimestampDeserializer.class)
    this.last = last;
    this.high = high;
    this.low = low;
    this.vwap = vwap;
    this.volume = volume;
    this.bid = bid;
    this.ask = ask;

    try {
      SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX");
      this.timestamp = myFormatter.parse(timestamp);
    } catch (ParseException e) {
      System.out.println(
          "Received parsing exception while attempting to process timestamp: " + e.getMessage());
    }
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

  public BigDecimal getVwap() {

    return vwap;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {

    return "IndependentReserveTicker [last="
        + last
        + ", high="
        + high
        + ", low="
        + low
        + ", vwap="
        + vwap
        + ", volume="
        + volume
        + ", bid="
        + bid
        + ", ask="
        + ask
        + ", timestamp="
        + timestamp
        + "]";
  }
}
