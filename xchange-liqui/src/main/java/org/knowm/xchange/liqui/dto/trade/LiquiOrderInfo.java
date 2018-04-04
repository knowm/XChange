package org.knowm.xchange.liqui.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.dto.LiquiTradeType;

public class LiquiOrderInfo {

  private final CurrencyPair pair;
  private final LiquiTradeType type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final long timestampCreated;
  private final BigDecimal startAmount;
  private final String status;

  public LiquiOrderInfo(
      @JsonProperty("start_amount") final String startAmount,
      @JsonProperty("status") final String status,
      @JsonProperty("pair") final String pair,
      @JsonProperty("type") final LiquiTradeType type,
      @JsonProperty("amount") final String amount,
      @JsonProperty("rate") final String rate,
      @JsonProperty("timestamp_created") final long timestampCreated) {
    this.status = status;
    final String[] split = pair.split("_");
    this.pair = new CurrencyPair(split[0], split[1]);
    this.type = type;
    this.startAmount = new BigDecimal(startAmount != null ? startAmount : "0");
    this.amount = new BigDecimal(amount);
    this.rate = new BigDecimal(rate);
    this.timestampCreated = timestampCreated;
  }

  public BigDecimal getStartAmount() {
    return startAmount;
  }

  public String getStatus() {
    return status;
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

  public long getTimestampCreated() {
    return timestampCreated;
  }

  @Override
  public String toString() {
    return "LiquiOrderInfo{"
        + "pair="
        + pair
        + ", type="
        + type
        + ", amount="
        + amount
        + ", rate="
        + rate
        + ", timestampCreated="
        + timestampCreated
        + ", startAmount='"
        + startAmount
        + '\''
        + ", status='"
        + status
        + '\''
        + '}';
  }
}
