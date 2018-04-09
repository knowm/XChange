package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class OkCoinFuturesTradeHistoryResult extends OkCoinErrorResult {

  private final long tid;
  private final long amount;
  private final long timestamp;
  private final TransactionType type;
  /** USD amount, negative -> BID, positive -> ASK */
  private final BigDecimal price;

  /**
   * Constructor
   *
   * @param amount
   * @param price
   * @param type
   * @param tid
   */
  public OkCoinFuturesTradeHistoryResult(
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("date") long timestamp,
      @JsonProperty("amount") long amount,
      @JsonProperty("tid") long tid,
      @JsonProperty("type") TransactionType type,
      @JsonProperty("price") BigDecimal price) {

    super(true, errorCode);
    this.tid = tid;
    this.amount = amount;
    this.timestamp = timestamp;
    this.type = type;
    this.price = price;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public long getId() {

    return tid;
  }

  public TransactionType getType() {

    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public long getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return String.format(
        "UserTransaction{timestamp=%s, tid=%d, type=%s, price=%s, amount=%s}",
        timestamp, tid, type, price);
  }

  public enum TransactionType {
    buy,
    sell /*
          * reseved so parsing won 't break in case Bitstamp adds new types
          */
  }
}
