package org.knowm.xchange.ripple.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.UserTrade;

public class RippleUserTrade extends UserTrade {

  private final String baseCounterparty;
  private final String counterCounterparty;

  private final BigDecimal baseTransferFee;
  private final BigDecimal counterTransferFee;

  public RippleUserTrade(
      final OrderType type,
      final BigDecimal originalAmount,
      final CurrencyPair currencyPair,
      final BigDecimal price,
      final Date timestamp,
      final String id,
      final String orderId,
      final BigDecimal feeAmount,
      final Currency feeCurrency,
      final String baseCounterparty,
      final String counterCounterparty,
      final BigDecimal baseTransferFee,
      final BigDecimal counterTransferFee) {
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
    this.baseCounterparty = baseCounterparty;
    this.counterCounterparty = counterCounterparty;

    this.baseTransferFee = baseTransferFee;
    this.counterTransferFee = counterTransferFee;
  }

  public String getBaseCounterparty() {
    return baseCounterparty;
  }

  public String getCounterCounterparty() {
    return counterCounterparty;
  }

  public BigDecimal getBaseTransferFee() {
    return baseTransferFee;
  }

  public Currency getBaseTransferFeeCurrency() {
    return currencyPair.base;
  }

  public BigDecimal getCounterTransferFee() {
    return counterTransferFee;
  }

  public Currency getCounterTransferFeeCurrency() {
    return currencyPair.counter;
  }

  public static class Builder extends UserTrade.Builder {

    private String baseCounterparty = "";
    private String counterCounterparty = "";

    private BigDecimal baseTransferFee = BigDecimal.ZERO;
    private BigDecimal counterTransferFee = BigDecimal.ZERO;

    public static Builder from(final RippleUserTrade trade) {
      final Builder builder =
          new Builder()
              .baseCounterparty(trade.getBaseCounterparty())
              .counterCounterparty(trade.getCounterCounterparty());
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

    public Builder baseCounterparty(final String value) {
      baseCounterparty = value;
      return this;
    }

    public Builder counterCounterparty(final String value) {
      counterCounterparty = value;
      return this;
    }

    public Builder baseTransferFee(final BigDecimal value) {
      baseTransferFee = value;
      return this;
    }

    public Builder counterTransferFee(final BigDecimal value) {
      counterTransferFee = value;
      return this;
    }

    @Override
    public RippleUserTrade build() {
      final RippleUserTrade trade =
          new RippleUserTrade(
              type,
              originalAmount,
              currencyPair,
              price,
              timestamp,
              id,
              orderId,
              feeAmount,
              feeCurrency,
              baseCounterparty,
              counterCounterparty,
              baseTransferFee,
              counterTransferFee);
      return trade;
    }
  }
}
