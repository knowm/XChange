package org.knowm.xchange.bx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BxTicker {

  private final String pairingId;
  private final String primaryCurrency;
  private final String secondaryCurrency;
  private final BigDecimal change;
  private final BigDecimal lastPrice;
  private final BigDecimal volume24hours;
  private final BxOrderBook orderBook;
  private BigDecimal open = null;
  private BigDecimal high = null;
  private BigDecimal low = null;
  private BigDecimal avg = null;
  private BigDecimal volume = null;

  public BxTicker(
      @JsonProperty("pairing_id") String pairingId,
      @JsonProperty("primary_currency") String primaryCurrency,
      @JsonProperty("secondary_currency") String secondaryCurrency,
      @JsonProperty("change") BigDecimal change,
      @JsonProperty("last_price") BigDecimal lastPrice,
      @JsonProperty("volume_24hours") BigDecimal volume24hours,
      @JsonProperty("orderbook") BxOrderBook orderBook) {
    this.pairingId = pairingId;
    this.primaryCurrency = primaryCurrency;
    this.secondaryCurrency = secondaryCurrency;
    this.change = change;
    this.lastPrice = lastPrice;
    this.volume24hours = volume24hours;
    this.orderBook = orderBook;
  }

  public String getPairingId() {
    return pairingId;
  }

  public String getPrimaryCurrency() {
    return primaryCurrency;
  }

  public String getSecondaryCurrency() {
    return secondaryCurrency;
  }

  public BigDecimal getChange() {
    return change;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getVolume24hours() {
    return volume24hours;
  }

  public BxOrderBook getOrderBook() {
    return orderBook;
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

  public BigDecimal getAvg() {
    return avg;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public void setAvg(BigDecimal avg) {
    this.avg = avg;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  @Override
  public String toString() {
    return "BxTicker{"
        + "pairingId='"
        + pairingId
        + '\''
        + ", primaryCurrency='"
        + primaryCurrency
        + '\''
        + ", secondaryCurrency='"
        + secondaryCurrency
        + '\''
        + ", change="
        + change
        + ", lastPrice="
        + lastPrice
        + ", volume24hours="
        + volume24hours
        + ", orderBook="
        + orderBook
        + '}';
  }
}
