package org.knowm.xchange.anx.v2.dto.trade;

import org.knowm.xchange.anx.v2.dto.ANXValue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing Open Orders from ANX
 */
public final class ANXOpenOrder {

  private final String oid;
  private final String currency;
  private final String item;
  private final String type;
  private final ANXValue amount;
  private final ANXValue effectiveAmount;
  private final ANXValue invalidAmount;
  private final ANXValue price;
  private final String status;
  private final long date;
  private final long priority;

  /**
   * Constructor
   *
   * @param oid
   * @param currency
   * @param item
   * @param type
   * @param amount
   * @param invalidAmount
   * @param price
   * @param status
   * @param date
   * @param priority
   */
  public ANXOpenOrder(@JsonProperty("oid") String oid, @JsonProperty("currency") String currency, @JsonProperty("item") String item,
      @JsonProperty("type") String type, @JsonProperty("amount") ANXValue amount, @JsonProperty("effective_amount") ANXValue effectiveAmount,
      @JsonProperty("invalid_amount") ANXValue invalidAmount, @JsonProperty("price") ANXValue price, @JsonProperty("status") String status,
      @JsonProperty("date") long date, @JsonProperty("priority") long priority) {

    this.oid = oid;
    this.currency = currency;
    this.item = item;
    this.type = type;
    this.amount = amount;
    this.effectiveAmount = effectiveAmount;
    this.invalidAmount = invalidAmount;
    this.price = price;
    this.status = status;
    this.date = date;
    this.priority = priority;
  }

  public String getOid() {

    return oid;
  }

  public String getCurrency() {

    return currency;
  }

  public String getItem() {

    return item;
  }

  public String getType() {

    return type;
  }

  public ANXValue getAmount() {

    return amount;
  }

  public ANXValue getEffectiveAmount() {

    return effectiveAmount;
  }

  public ANXValue getInvalidAmount() {

    return invalidAmount;
  }

  public ANXValue getPrice() {

    return price;
  }

  public String getStatus() {

    return status;
  }

  public long getDate() {

    return date;
  }

  public long getPriority() {

    return priority;
  }

  @Override
  public String toString() {

    return "ANXOpenOrder [oid=" + oid + ", currency=" + currency + ", item=" + item + ", type=" + type + ", amount=" + amount + ", effectiveAmount="
        + effectiveAmount + ", invalidAmount=" + invalidAmount + ", price=" + price + ", status=" + status + ", date=" + date + ", priority="
        + priority + "]";
  }

}
