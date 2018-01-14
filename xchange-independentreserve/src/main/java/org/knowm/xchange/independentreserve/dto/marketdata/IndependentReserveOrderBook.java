package org.knowm.xchange.independentreserve.dto.marketdata;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * Author: Kamil Zbikowski Date: 4/9/15
 */
public class IndependentReserveOrderBook {
  private final Date createdTimestamp;
  private final List<OrderBookOrder> buyOrders;
  private final List<OrderBookOrder> sellOrders;

  private final String primaryCurrencyCode;
  private final String secondaryCurrencyCode;

  public IndependentReserveOrderBook(@JsonProperty("BuyOrders") List<OrderBookOrder> buyOrders,
      @JsonProperty("SellOrders") List<OrderBookOrder> sellOrders, @JsonProperty("PrimaryCurrencyCode") String primaryCurrencyCode,
      @JsonProperty("SecondaryCurrencyCode") String secondaryCurrencyCode,
      @JsonProperty("CreatedTimestampUtc") String createdTimestampUtc) throws com.fasterxml.jackson.databind.exc.InvalidFormatException, ParseException {
    this.buyOrders = buyOrders;
    this.createdTimestamp = org.knowm.xchange.utils.DateUtils.fromISO8601DateString(createdTimestampUtc) ;
    this.sellOrders = sellOrders;
    this.primaryCurrencyCode = primaryCurrencyCode;
    this.secondaryCurrencyCode = secondaryCurrencyCode;
  }

  public List<OrderBookOrder> getBuyOrders() {
    return buyOrders;
  }

  public Date getCreatedTimestamp() {
    return createdTimestamp;
  }

  public String getPrimaryCurrencyCode() {
    return primaryCurrencyCode;
  }

  public String getSecondaryCurrencyCode() {
    return secondaryCurrencyCode;
  }

  public List<OrderBookOrder> getSellOrders() {
    return sellOrders;
  }
}
