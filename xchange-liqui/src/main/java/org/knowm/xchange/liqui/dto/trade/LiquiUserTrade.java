package org.knowm.xchange.liqui.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.dto.LiquiTradeType;

public class LiquiUserTrade {

  private final CurrencyPair pair;
  private final long tradeId;
  private final LiquiTradeType type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final long orderId;
  private final boolean yourOrder;
  private final long timestamp;

  public LiquiUserTrade(
      @JsonProperty("pair") final String pair,
      @JsonProperty("type") final LiquiTradeType type,
      @JsonProperty("amount") final String amount,
      @JsonProperty("rate") final String rate,
      @JsonProperty("order_id") final long orderId,
      @JsonProperty("is_your_order") final boolean yourOrder,
      @JsonProperty("timestamp") final long timestamp,
      @JsonProperty("trade_id") final long tradeId) {
    final String[] split = pair.split("_");
    this.pair = new CurrencyPair(split[0], split[1]);
    this.type = type;
    this.amount = new BigDecimal(amount);
    this.rate = new BigDecimal(rate);
    this.orderId = orderId;
    this.yourOrder = yourOrder;
    this.timestamp = timestamp;
    this.tradeId = tradeId;
  }

  public CurrencyPair getPair() {
    return pair;
  }

  public LiquiTradeType getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public long getOrderId() {
    return orderId;
  }

  public boolean isYourOrder() {
    return yourOrder;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public long getTradeId() {
    return tradeId;
  }

  @Override
  public String toString() {
    return "LiquiUserTrade{"
        + "pair="
        + pair
        + ", tradeId="
        + tradeId
        + ", type="
        + type
        + ", amount="
        + amount
        + ", rate="
        + rate
        + ", orderId="
        + orderId
        + ", yourOrder="
        + yourOrder
        + ", timestamp="
        + timestamp
        + '}';
  }
}
