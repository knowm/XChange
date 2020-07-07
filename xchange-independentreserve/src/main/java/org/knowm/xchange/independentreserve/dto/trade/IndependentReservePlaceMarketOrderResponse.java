package org.knowm.xchange.independentreserve.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class IndependentReservePlaceMarketOrderResponse {
  private final Date createdTimestampUtc;
  private final String orderGuid;
  private final String primaryCurrencyCode;
  private final BigDecimal reservedAmount;
  private final String secondaryCurrencyCode;
  private final String status;
  private final String type;
  private final BigDecimal volumeFilled;
  private final BigDecimal volumeOrdered;

  public IndependentReservePlaceMarketOrderResponse(
      @JsonProperty("CreatedTimestampUtc") Date createdTimestampUtc,
      @JsonProperty("OrderGuid") String orderGuid,
      @JsonProperty("PrimaryCurrencyCode") String primaryCurrencyCode,
      @JsonProperty("ReservedAmount") BigDecimal reservedAmount,
      @JsonProperty("SecondaryCurrencyCode") String secondaryCurrencyCode,
      @JsonProperty("Status") String status,
      @JsonProperty("Type") String type,
      @JsonProperty("VolumeFilled") BigDecimal volumeFilled,
      @JsonProperty("VolumeOrdered") BigDecimal volumeOrdered) {

    this.createdTimestampUtc = createdTimestampUtc;
    this.orderGuid = orderGuid;
    this.primaryCurrencyCode = primaryCurrencyCode;
    this.reservedAmount = reservedAmount;
    this.secondaryCurrencyCode = secondaryCurrencyCode;
    this.status = status;
    this.type = type;
    this.volumeFilled = volumeFilled;
    this.volumeOrdered = volumeOrdered;
  }

  public Date getCreatedTimestampUtc() {
    return createdTimestampUtc;
  }

  public String getOrderGuid() {
    return orderGuid;
  }

  public String getPrimaryCurrencyCode() {
    return primaryCurrencyCode;
  }

  public BigDecimal getReservedAmount() {
    return reservedAmount;
  }

  public String getSecondaryCurrencyCode() {
    return secondaryCurrencyCode;
  }

  public String getStatus() {
    return status;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getVolumeFilled() {
    return volumeFilled;
  }

  public BigDecimal getVolumeOrdered() {
    return volumeOrdered;
  }
}
