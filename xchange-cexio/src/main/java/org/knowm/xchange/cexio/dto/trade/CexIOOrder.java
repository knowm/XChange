package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Author: brox Since: 2/5/14 */
public class CexIOOrder {

  private final long id;
  private final long time;
  private final Type type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal pending;
  private final String errorMessage;

  /** non-JSON fields */
  private String tradableIdentifier;

  private String transactionCurrency;

  /**
   * Constructor
   *
   * @param id
   * @param time
   * @param type
   * @param price
   * @param amount
   * @param pending
   */
  public CexIOOrder(
      @JsonProperty("id") long id,
      @JsonProperty("time") long time,
      @JsonProperty("type") Type type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("pending") BigDecimal pending,
      @JsonProperty("error") String errorMessage) {

    this.id = id;
    this.time = time;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.pending = pending;
    this.errorMessage = errorMessage;
  }

  public long getId() {

    return id;
  }

  public long getTime() {

    return time;
  }

  public Type getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getPending() {

    return pending;
  }

  public String getErrorMessage() {

    return errorMessage;
  }

  public String getTradableIdentifier() {

    return tradableIdentifier;
  }

  public void setTradableIdentifier(String tradableIdentifier) {

    this.tradableIdentifier = tradableIdentifier;
  }

  public String getTransactionCurrency() {

    return transactionCurrency;
  }

  public void setTransactionCurrency(String transactionCurrency) {

    this.transactionCurrency = transactionCurrency;
  }

  @Override
  public String toString() {

    return errorMessage != null
        ? errorMessage
        : String.format(
            "Order{id=%s, time=%s, type=%s, price=%s, amount=%s, pending=%s}",
            id, time, type, price, amount, pending);
  }

  public enum Type {
    buy,
    sell
  }
}
