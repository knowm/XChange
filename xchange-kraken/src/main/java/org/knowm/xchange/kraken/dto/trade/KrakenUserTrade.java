package org.knowm.xchange.kraken.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;

public class KrakenUserTrade extends UserTrade {

  private final BigDecimal cost;

  public KrakenUserTrade(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigDecimal price,
      Date timestamp,
      String id,
      String orderId,
      BigDecimal feeAmount,
      Currency feeCurrency,
      BigDecimal cost) {
    super(
        type,
        originalAmount,
        currencyPair,
        price,
        timestamp,
        id,
        orderId,
        feeAmount,
        feeCurrency,
        "");
    this.cost = cost;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public static class Builder extends UserTrade.Builder {

    private BigDecimal cost;

    public static Builder from(KrakenUserTrade trade) {
      Builder builder = new Builder().cost(trade.getCost());
      builder
          .orderId(trade.getOrderId())
          .feeAmount(trade.getFeeAmount())
          .feeCurrency(trade.getFeeCurrency());
      builder
          .type(trade.getType())
          .originalAmount(trade.getOriginalAmount())
          .currencyPair(trade.getCurrencyPair())
          .price(trade.getPrice())
          .timestamp(trade.getTimestamp())
          .id(trade.getId());
      return builder;
    }

    public Builder cost(BigDecimal cost) {
      this.cost = cost;
      return this;
    }

    public KrakenUserTrade build() {
      KrakenUserTrade trade =
          new KrakenUserTrade(
              type,
              originalAmount,
              currencyPair,
              price,
              timestamp,
              id,
              orderId,
              feeAmount,
              feeCurrency,
              cost);
      return trade;
    }
  }
}
