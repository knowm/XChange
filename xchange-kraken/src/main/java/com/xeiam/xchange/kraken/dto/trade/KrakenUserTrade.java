package com.xeiam.xchange.kraken.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;

public class KrakenUserTrade extends UserTrade {

  private final BigDecimal cost;

  public KrakenUserTrade(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id,
      String orderId, BigDecimal feeAmount, Currency feeCurrency, BigDecimal cost) {
    super(type, tradableAmount, currencyPair, price, timestamp, id, orderId, feeAmount, feeCurrency);
    this.cost = cost;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public static class Builder extends UserTrade.Builder {

    private BigDecimal cost;

    public static Builder from(KrakenUserTrade trade) {
      Builder builder = new Builder().cost(trade.getCost());
      builder.orderId(trade.getOrderId()).feeAmount(trade.getFeeAmount()).feeCurrency(trade.getFeeCurrency());
      builder.type(trade.getType()).tradableAmount(trade.getTradableAmount()).currencyPair(trade.getCurrencyPair()).price(trade.getPrice())
          .timestamp(trade.getTimestamp()).id(trade.getId());
      return builder;
    }

    public Builder cost(BigDecimal cost) {
      this.cost = cost;
      return this;
    }

    public KrakenUserTrade build() {
      KrakenUserTrade trade = new KrakenUserTrade(type, tradableAmount, currencyPair, price, timestamp, id, orderId, feeAmount, feeCurrency, cost);
      return trade;
    }
  }

}
