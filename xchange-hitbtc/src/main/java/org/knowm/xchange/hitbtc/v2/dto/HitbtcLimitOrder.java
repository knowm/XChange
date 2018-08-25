package org.knowm.xchange.hitbtc.v2.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

public class HitbtcLimitOrder extends LimitOrder {
  public final BigInteger hitbtcOrderId;

  public HitbtcLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigInteger id,
      Date timestamp,
      BigDecimal limitPrice,
      String clientOrderId) {
    super(type, originalAmount, currencyPair, clientOrderId, timestamp, limitPrice);
    this.hitbtcOrderId = id;
  }

  public HitbtcLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      BigDecimal cumulativeAmount,
      CurrencyPair currencyPair,
      BigInteger id,
      Date timestamp,
      BigDecimal limitPrice,
      String clientOrderId) {
    super(
        type, originalAmount, cumulativeAmount, currencyPair, clientOrderId, timestamp, limitPrice);
    this.hitbtcOrderId = id;
  }

  public HitbtcLimitOrder(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigInteger id,
      Date timestamp,
      BigDecimal limitPrice,
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
        limitPrice,
        averagePrice,
        cumulativeAmount,
        fee,
        status);
    this.hitbtcOrderId = id;
  }

  public BigInteger getHitbtcOrderId() {
    return hitbtcOrderId;
  }
}
