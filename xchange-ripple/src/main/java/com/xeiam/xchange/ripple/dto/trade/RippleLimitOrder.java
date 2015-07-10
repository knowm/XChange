package com.xeiam.xchange.ripple.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class RippleLimitOrder extends LimitOrder {

  private final String baseCounterparty;
  private final String counterCounterparty;

  public RippleLimitOrder(final OrderType type, final BigDecimal tradableAmount, final CurrencyPair currencyPair, final String id,
      final Date timestamp, final BigDecimal limitPrice, final String baseCounterparty, final String counterCounterparty) {
    super(type, tradableAmount, currencyPair, id, timestamp, limitPrice);
    this.baseCounterparty = baseCounterparty;
    this.counterCounterparty = counterCounterparty;
  }

  public String getBaseCounterparty() {
    return baseCounterparty;
  }

  public String getCounterCounterparty() {
    return counterCounterparty;
  }

  public static class Builder extends LimitOrder.Builder {

    private String baseCounterparty = "";
    private String counterCounterparty = "";

    public Builder(final OrderType type, final CurrencyPair currencyPair) {
      super(type, currencyPair);
    }

    public static Builder from(final RippleLimitOrder order) {
      final Builder builder = new Builder(order.getType(), order.getCurrencyPair()).baseCounterparty(order.getBaseCounterparty())
          .counterCounterparty(order.getCounterCounterparty());
      builder.id(order.getId()).orderType(order.getType()).tradableAmount(order.getTradableAmount()).currencyPair(order.getCurrencyPair())
          .limitPrice(order.getLimitPrice()).timestamp(order.getTimestamp()).id(order.getId()).flags(order.getOrderFlags());
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

    public RippleLimitOrder build() {
      final RippleLimitOrder order = new RippleLimitOrder(orderType, tradableAmount, currencyPair, id, timestamp, limitPrice, baseCounterparty,
          counterCounterparty);
      order.setOrderFlags(flags);
      return order;
    }
  }
}
