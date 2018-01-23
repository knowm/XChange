package org.knowm.xchange.independentreserve.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Author: Kamil Zbikowski Date: 4/16/15
 */
public class IndependentReserveTrade {
  private final String tradeGuid;
  private final Date tradeTimestamp;
  private final String orderGuid;
  private final String orderType;
  private final Date orderTimestamp;
  private final BigDecimal volumeTraded;
  private final BigDecimal price;
  private final String primaryCurrencyCode;
  private final String secondaryCurrencyCode;

  public IndependentReserveTrade(@JsonProperty("OrderGuid") String orderGuid, @JsonProperty("TradeGuid") String tradeGuid,
      @JsonProperty("TradeTimestampUtc") String tradeTimestampUtc, @JsonProperty("OrderType") String orderType,
      @JsonProperty("OrderTimestampUtc") String orderTimestampUtc, @JsonProperty("VolumeTraded") BigDecimal volumeTraded,
      @JsonProperty("Price") BigDecimal price, @JsonProperty("PrimaryCurrencyCode") String primaryCurrencyCode,
      @JsonProperty("SecondaryCurrencyCode") String secondaryCurrencyCode) throws com.fasterxml.jackson.databind.exc.InvalidFormatException {
    this.orderGuid = orderGuid;
    this.tradeGuid = tradeGuid;
    tradeTimestamp = org.knowm.xchange.utils.DateUtils.fromISO8601DateString(tradeTimestampUtc);
    this.orderType = orderType;
    orderTimestamp = org.knowm.xchange.utils.DateUtils.fromISO8601DateString(orderTimestampUtc);
    this.volumeTraded = volumeTraded;
    this.price = price;
    this.primaryCurrencyCode = primaryCurrencyCode;
    this.secondaryCurrencyCode = secondaryCurrencyCode;
  }

  public String getOrderGuid() {
    return orderGuid;
  }

  public Date getOrderTimestampUtc() {
    return orderTimestamp;
  }

  public String getOrderType() {
    return orderType;
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

  public String getTradeGuid() {
    return tradeGuid;
  }

  public Date getTradeTimestamp() {
    return tradeTimestamp;
  }

  public BigDecimal getVolumeTraded() {
    return volumeTraded;
  }
}
