package org.knowm.xchange.anx.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Market Depth Change from ANX
 * <p>
 * Auto-generated using the simplest types possible with conversion delegated to the adapter
 * <p>
 * Note: deprecated fields are not included in this value object
 */
public final class ANXDepthUpdate {

  private final String tradeType;
  private final long priceInt;
  private final long volumeInt;
  private final String item;
  private final String currency;
  private final long now;
  private final long totalVolumeInt;

  /**
   * Constructor
   *
   * @param tradeType
   * @param priceInt
   * @param volumeInt
   * @param item
   * @param currency
   * @param now
   * @param totalVolumeInt
   */
  public ANXDepthUpdate(@JsonProperty("type_str") String tradeType, @JsonProperty("price_int") long priceInt,
      @JsonProperty("volume_int") long volumeInt, @JsonProperty("item") String item, @JsonProperty("currency") String currency,
      @JsonProperty("now") long now, @JsonProperty("total_volume_int") long totalVolumeInt) {

    this.tradeType = tradeType;
    this.priceInt = priceInt;
    this.volumeInt = volumeInt;
    this.item = item;
    this.currency = currency;
    this.now = now;
    this.totalVolumeInt = totalVolumeInt;
  }

  public String getTradeType() {

    return tradeType;
  }

  public long getPriceInt() {

    return priceInt;
  }

  public long getVolumeInt() {

    return volumeInt;
  }

  public String getItem() {

    return item;
  }

  public String getCurrency() {

    return currency;
  }

  public long getNow() {

    return now;
  }

  public long getTotalVolumeInt() {

    return totalVolumeInt;
  }

  @Override
  public String toString() {

    return "ANXDepthUpdate [tradeType=" + tradeType + ", priceInt=" + priceInt + ", volumeInt=" + volumeInt + ", item=" + item + ", currency="
        + currency + ", now=" + now + ", totalVolumeInt=" + totalVolumeInt + "]";
  }

}
