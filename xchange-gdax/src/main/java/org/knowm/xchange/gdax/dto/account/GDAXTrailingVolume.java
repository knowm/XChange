package org.knowm.xchange.gdax.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class GDAXTrailingVolume {

  @JsonProperty("product_id")
  private final String productID;

  @JsonProperty("exchange_volume")
  private final BigDecimal exchangeVolume;

  @JsonProperty("volume")
  private final BigDecimal volume;

  @JsonProperty("recorded_at")
  private final Date recordedAt;

  public GDAXTrailingVolume(
      String productId, BigDecimal exchangeVolume, BigDecimal volume, Date recordedAt) {
    this.productID = productId;
    this.exchangeVolume = exchangeVolume;
    this.volume = volume;
    this.recordedAt = recordedAt;
  }

  public String getProductID() {
    return productID;
  }

  public BigDecimal getExchangeVolume() {
    return exchangeVolume;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public Date getRecordedAt() {
    return recordedAt;
  }

  @Override
  public String toString() {
    return "GDAXTrailingVolume{"
        + "product_id='"
        + productID
        + '\''
        + ", exchange_volume='"
        + exchangeVolume
        + '\''
        + ", volume="
        + volume
        + ", recorded_at='"
        + recordedAt
        + '\''
        + '}';
  }
}
