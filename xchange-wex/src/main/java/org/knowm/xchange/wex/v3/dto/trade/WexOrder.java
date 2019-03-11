package org.knowm.xchange.wex.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.MessageFormat;

/** @author Matija Mazi */
public class WexOrder {

  private final String pair;
  private final Type type;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final Long timestampCreated;
  private final int status; // deprecated, always 0

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
  public WexOrder(
      @JsonProperty("status") int status,
      @JsonProperty("timestamp_created") Long timestampCreated,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("type") Type type,
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

  @Deprecated
  public int getStatus() {

    return status;
  }

  @Override
  public String toString() {

    return MessageFormat.format(
        "WexOrder[pair=''{0}'', type={1}, amount={2}, rate={3}, timestampCreated={4}, status={5}]",
        pair, type, amount, rate, timestampCreated, status);
  }

  public enum Type {
    buy,
    sell
  }
}
