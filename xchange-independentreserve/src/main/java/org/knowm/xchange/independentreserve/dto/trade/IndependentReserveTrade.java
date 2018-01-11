package org.knowm.xchange.independentreserve.dto.trade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

import com.fasterxml.jackson.annotation.JsonProperty;


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
      @JsonProperty("SecondaryCurrencyCode") String secondaryCurrencyCode) throws ParseException {
    this.orderGuid = orderGuid;
    this.tradeGuid = tradeGuid;
    tradeTimestamp = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mmX").parse (tradeTimestampUtc);
    this.orderType = orderType;
    orderTimestamp = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mmX").parse(orderTimestampUtc);
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
