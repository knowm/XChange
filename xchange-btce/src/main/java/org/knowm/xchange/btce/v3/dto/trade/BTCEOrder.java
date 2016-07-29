package org.knowm.xchange.btce.v3.dto.trade;

import java.math.BigDecimal;
import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public class BTCEOrder {

  private final String pair;
  private final Type type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final Long timestampCreated;
  /** 0: active; 1: ??; 2: cancelled */
  private final int status;

  /**
   * Constructor
   * 
   * @param status
   * @param timestampCreated
   * @param rate
   * @param amount
   * @param type
   * @param pair
   */
  public BTCEOrder(@JsonProperty("status") int status, @JsonProperty("timestamp_created") Long timestampCreated,
      @JsonProperty("rate") BigDecimal rate, @JsonProperty("amount") BigDecimal amount, @JsonProperty("type") Type type,
      @JsonProperty("pair") String pair) {

    this.status = status;
    this.timestampCreated = timestampCreated;
    this.rate = rate;
    this.amount = amount;
    this.type = type;
    this.pair = pair;
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

  public Long getTimestampCreated() {

    return timestampCreated;
  }

  public int getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return MessageFormat.format("BTCEOrder[pair=''{0}'', type={1}, amount={2}, rate={3}, timestampCreated={4}, status={5}]", pair, type, amount, rate,
        timestampCreated, status);
  }

  public static enum Type {
    buy, sell
  }
}
