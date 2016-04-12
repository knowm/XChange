package org.knowm.xchange.btcchina.dto.trade.streaming;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class BTCChinaOrder extends Order {

  private final BigDecimal limitPrice;
  private final BigDecimal amountOriginal;
  private final OrderStatus status;

  public BTCChinaOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice,
      BigDecimal amountOriginal, OrderStatus status) {

    super(type, tradableAmount, currencyPair, id, timestamp);
    this.limitPrice = limitPrice;
    this.amountOriginal = amountOriginal;
    this.status = status;
  }

  public BigDecimal getLimitPrice() {

    return limitPrice;
  }

  public BigDecimal getAmountOriginal() {

    return amountOriginal;
  }

  @Override
  public OrderStatus getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return "BTCChinaOrder [limitPrice=" + limitPrice + ", amountOriginal=" + amountOriginal + ", status=" + status + ", " + super.toString() + "]";
  }

}
