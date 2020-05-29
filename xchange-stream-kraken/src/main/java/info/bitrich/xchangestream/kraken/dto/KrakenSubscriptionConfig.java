package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;

/** @author makarid, pchertalev */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenSubscriptionConfig {

  /**
   * ticker|ohlc|trade|book|spread|ownTrades|openOrders|*, * for all available channels depending on
   * the connected environment (ohlc interval value is 1 if all public channels subscribed)
   */
  private KrakenSubscriptionName name;

  /**
   * Optional - depth associated with book subscription in number of levels each side, default 10.
   * Valid Options are: 10, 25, 100, 500, 1000
   */
  private Integer depth;

  /** Optional, base64-encoded authentication token for private-data endpoints. */
  private String token;

  /**
   * Optional - Time interval associated with ohlc subscription in minutes. Default 1. Valid
   * Interval values: 1|5|15|30|60|240|1440|10080|21600
   */
  private Integer interval;

  public KrakenSubscriptionConfig(KrakenSubscriptionName name) {
    this(name, null, null);
  }

  @JsonCreator
  public KrakenSubscriptionConfig(
      @JsonProperty("name") KrakenSubscriptionName name,
      @JsonProperty("depth") Integer depth,
      @JsonProperty("token") String token) {
    this.name = name;
    this.depth = depth;
    this.token = token;
  }

  public KrakenSubscriptionName getName() {
    return name;
  }

  public void setName(KrakenSubscriptionName name) {
    this.name = name;
  }

  public Integer getDepth() {
    return depth;
  }

  public void setDepth(Integer depth) {
    this.depth = depth;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Integer getInterval() {
    return interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }
}
