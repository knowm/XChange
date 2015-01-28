package com.xeiam.xchange.btcchina.dto.trade.streaming;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrderStatus;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;

public class BTCChinaOrder extends Order {

  private final BigDecimal limitPrice;
  private final BigDecimal amountOriginal;
  private final BTCChinaOrderStatus status;

  public BTCChinaOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal limitPrice,
      BigDecimal amountOriginal, BTCChinaOrderStatus status) {

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

  public BTCChinaOrderStatus getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return "BTCChinaOrder [limitPrice=" + limitPrice + ", amountOriginal=" + amountOriginal + ", status=" + status + ", " + super.toString() + "]";
  }

}
