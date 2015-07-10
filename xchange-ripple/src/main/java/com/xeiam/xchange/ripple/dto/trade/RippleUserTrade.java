package com.xeiam.xchange.ripple.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.UserTrade;

public class RippleUserTrade extends UserTrade {

  private final String baseCounterparty;
  private final String counterCounterparty;

  public RippleUserTrade(final OrderType type, final BigDecimal tradableAmount, final CurrencyPair currencyPair, final BigDecimal price,
      final Date timestamp, final String id, final String orderId, final BigDecimal feeAmount, final String feeCurrency,
      final String baseCounterparty, final String counterCounterparty) {
    super(type, tradableAmount, currencyPair, price, timestamp, id, orderId, feeAmount, feeCurrency);
    this.baseCounterparty = baseCounterparty;
    this.counterCounterparty = counterCounterparty;
  }

  public String getBaseCounterparty() {
    return baseCounterparty;
  }

  public String getCounterCounterparty() {
    return counterCounterparty;
  }

  public static class Builder extends UserTrade.Builder {

    private String baseCounterparty = "";
    private String counterCounterparty = "";

    public static Builder from(final RippleUserTrade trade) {
      Builder builder = new Builder().baseCounterparty(trade.getBaseCounterparty()).counterCounterparty(trade.getCounterCounterparty());
      builder.orderId(trade.getOrderId()).feeAmount(trade.getFeeAmount()).feeCurrency(trade.getFeeCurrency());
      builder.type(trade.getType()).tradableAmount(trade.getTradableAmount()).currencyPair(trade.getCurrencyPair()).price(trade.getPrice())
          .timestamp(trade.getTimestamp()).id(trade.getId());
      return builder;
    }

    public Builder baseCounterparty(final String value) {
      baseCounterparty = value;
      return this;
    }

    public Builder counterCounterparty(final String value) {
      counterCounterparty = value;
      return this;
    }

    public RippleUserTrade build() {
      RippleUserTrade trade = new RippleUserTrade(type, tradableAmount, currencyPair, price, timestamp, id, orderId, feeAmount, feeCurrency,
          baseCounterparty, counterCounterparty);
      return trade;
    }
  }
}
