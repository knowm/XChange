package org.knowm.xchange.coinsetter.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Trade object.
 */
public class CoinsetterTrade {

  private final BigDecimal price;
  private final BigDecimal size;
  private final String exchangeId;
  private final long tickId;
  private final long timeStamp;
  private final BigDecimal volume;
  private final BigDecimal volume24;

  public CoinsetterTrade(@JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size,
      @JsonProperty("exchangeId") String exchangeId, @JsonProperty("tickId") long tickId, @JsonProperty("timeStamp") long timeStamp,
      @JsonProperty("volume") BigDecimal volume, @JsonProperty("volume24") BigDecimal volume24) {

    this.price = price;
    this.size = size;
    this.exchangeId = exchangeId;
    this.tickId = tickId;
    this.timeStamp = timeStamp;
    this.volume = volume;
    this.volume24 = volume24;
  }

  /**
   * Returns the price.
   *
   * @return the price.
   */
  public BigDecimal getPrice() {

    return price;
  }

  /**
   * Returns the size.
   *
   * @return the size.
   */
  public BigDecimal getSize() {

    return size;
  }

  /**
   * Returns the exchange.
   *
   * @return the exchange.
   */
  public String getExchangeId() {

    return exchangeId;
  }

  /**
   * Returns the tick ID.
   * 
   * @return the tick ID.
   */
  public long getTickId() {

    return tickId;
  }

  /**
   * Returns the UTC time since January 1, 1970, 00:00:00. Value is in milliseconds, which is a Java timestamp format standard. To get the UNIX
   * timestamp format standard, simply divide by 1000.
   *
   * @return the UTC time.
   */
  public long getTimeStamp() {

    return timeStamp;
  }

  /**
   * Returns the trading volume between the last trade and the prior midnight ET.
   *
   * @return the trading volume between the last trade and the prior midnight ET.
   */
  public BigDecimal getVolume() {

    return volume;
  }

  /**
   * Returns the trading volume between the last trade and 24 hours prior.
   *
   * @return the trading volume between the last trade and 24 hours prior.
   */
  public BigDecimal getVolume24() {

    return volume24;
  }

  @Override
  public String toString() {

    return "CoinsetterTrade [price=" + price + ", size=" + size + ", exchangeId=" + exchangeId + ", tickId=" + tickId + ", timeStamp=" + timeStamp
        + ", volume=" + volume + ", volume24=" + volume24 + "]";
  }

}
