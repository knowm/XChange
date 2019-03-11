package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.MessageFormat;

/** @author Mikhail Wall */
public class DSXTradeHistoryResult {

  private final String pair;
  private final Type type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final Long orderId;
  private final Long timestamp;
  private BigDecimal commission;
  private String commissionCurrency;

  public DSXTradeHistoryResult(
      @JsonProperty("pair") String pair,
      @JsonProperty("type") Type type,
      @JsonProperty("volume") BigDecimal amount,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("orderId") Long orderId,
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("commissionCurrency") String commissionCurrency) {

    this.pair = pair;
    this.type = type;
    this.amount = amount;
    this.rate = rate;
    this.orderId = orderId;
    this.timestamp = timestamp;
    this.commission = commission;
    this.commissionCurrency = commissionCurrency;
  }

  public String getPair() {
    return pair;
  }

  public Type getType() {
    return type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public Long getOrderId() {
    return orderId;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public String getCommissionCurrency() {
    return commissionCurrency;
  }

  @Override
  public String toString() {

    return MessageFormat.format(
        "DSXOwnTransaction[pair=''{0}'', type={1}, amount={2}, rate={3}, orderId={4}, timestamp={5}, "
            + "commission={6}, commissionCurrency={7}]",
        pair, type, amount, rate, orderId, timestamp, commission, commissionCurrency);
  }

  public enum Type {
    buy,
    sell
  }
}
