package org.knowm.xchange.independentreserve.dto.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;



import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Author: Kamil Zbikowski Date: 4/15/15
 */
public class IndependentReserveOpenOrder {
  private final BigDecimal avgPrice;
  private final Date createdTimestamp;
  private final BigDecimal feePercent;
  private final String orderGuid;
  private final String orderType;
  private final BigDecimal outstanding;
  private final BigDecimal price;
  private final String primaryCurrencyCode;
  private final String secondaryCurrencyCode;
  private final String status;
  private final BigDecimal value;
  private final BigDecimal volume;

  public IndependentReserveOpenOrder(@JsonProperty("AvgPrice") BigDecimal avgPrice, @JsonProperty("CreatedTimestampUtc") String createdTimestampUtc,
      @JsonProperty("FeePercent") BigDecimal feePercent, @JsonProperty("OrderGuid") String orderGuid, @JsonProperty("OrderType") String orderType,
      @JsonProperty("Outstanding") BigDecimal outstanding, @JsonProperty("Price") BigDecimal price,
      @JsonProperty("PrimaryCurrencyCode") String primaryCurrencyCode, @JsonProperty("SecondaryCurrencyCode") String secondaryCurrencyCode,
      @JsonProperty("Status") String status, @JsonProperty("Value") BigDecimal value, @JsonProperty("Volume") BigDecimal volume) throws com.fasterxml.jackson.databind.exc.InvalidFormatException {
    this.avgPrice = avgPrice;
    this.createdTimestamp = org.knowm.xchange.utils.DateUtils.fromISO8601DateString(createdTimestampUtc) ;
    this.feePercent = feePercent;
    this.orderGuid = orderGuid;
    this.orderType = orderType;
    this.outstanding = outstanding;
    this.price = price;
    this.primaryCurrencyCode = primaryCurrencyCode;
    this.secondaryCurrencyCode = secondaryCurrencyCode;
    this.status = status;
    this.value = value;
    this.volume = volume;
  }

  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public Date getCreatedTimestamp() {
    return createdTimestamp;
  }

  public BigDecimal getFeePercent() {
    return feePercent;
  }

  public String getOrderGuid() {
    return orderGuid;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getOutstanding() {
    return outstanding;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getPrimaryCurrencyCode() {
    return primaryCurrencyCode;
  }

  public String getSecondaryCurrencyCode() {
    return secondaryCurrencyCode;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getValue() {
    return value;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  @Override
  public String toString() {
    return "IndependentReserveOpenOrder{" + "avgPrice=" + avgPrice + ", createdTimestamp=" + createdTimestamp + ", feePercent=" + feePercent
        + ", orderGuid='" + orderGuid + '\'' + ", orderType='" + orderType + '\'' + ", outstanding=" + outstanding + ", price=" + price
        + ", primaryCurrencyCode='" + primaryCurrencyCode + '\'' + ", secondaryCurrencyCode='" + secondaryCurrencyCode + '\'' + ", status='" + status
        + '\'' + ", value=" + value + ", volume=" + volume + '}';
  }
}
