package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankeraTicker {

  private final Integer id;
  private final String high;
  private final String low;
  private final String bid;
  private final String ask;
  private final String last;
  private final String volume;
  private final long timestamp;

  /**
   * Constructor
   *
   * @param id
   * @param high
   * @param low
   * @param bid
   * @param ask
   * @param last
   * @param volume
   * @param timestamp
   */
  public BankeraTicker(
      @JsonProperty("id") Integer id,
      @JsonProperty("high") String high,
      @JsonProperty("low") String low,
      @JsonProperty("bid") String bid,
      @JsonProperty("ask") String ask,
      @JsonProperty("last") String last,
      @JsonProperty("volume") String volume,
      @JsonProperty("timestamp") long timestamp) {

    this.id = id;
    this.high = high;
    this.low = low;
    this.bid = bid;
    this.ask = ask;
    this.last = last;
    this.volume = volume;
    this.timestamp = timestamp;
  }

  public Integer getId() {
    return id;
  }

  public String getHigh() {
    return high;
  }

  public String getLow() {
    return low;
  }

  public String getBid() {
    return bid;
  }

  public String getAsk() {
    return ask;
  }

  public String getLast() {
    return last;
  }

  public String getVolume() {
    return volume;
  }

  public long getTimestamp() {
    return timestamp;
  }
}
