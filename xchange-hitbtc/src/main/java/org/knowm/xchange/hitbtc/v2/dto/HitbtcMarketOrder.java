package org.knowm.xchange.hitbtc.v2.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.MarketOrder;

public class HitbtcMarketOrder extends MarketOrder {
  public final BigInteger hitbtcOrderId;

  public HitbtcMarketOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigInteger id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String clientOrderId) {
    super(
        type,
        originalAmount,
        currencyPair,
        clientOrderId,
        timestamp,
        averagePrice,
        cumulativeAmount,
        fee,
        status);
    this.hitbtcOrderId = id;
  }

  public HitbtcMarketOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigInteger id,
      Date timestamp,
      String clientOrderId) {
    super(type, originalAmount, currencyPair, clientOrderId, timestamp);
    this.hitbtcOrderId = id;
  }

  public HitbtcMarketOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      Date timestamp,
      String clientOrderId) {
    super(type, originalAmount, currencyPair, clientOrderId, timestamp);
    this.hitbtcOrderId = null;
  }

  public HitbtcMarketOrder(
      OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, String clientOrderId) {
    super(type, originalAmount, currencyPair, clientOrderId, null);
    this.hitbtcOrderId = null;
  }

  public BigInteger getHitbtcOrderId() {
    return hitbtcOrderId;
  }
}
