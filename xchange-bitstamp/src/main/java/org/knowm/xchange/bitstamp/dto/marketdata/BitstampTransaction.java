package org.knowm.xchange.bitstamp.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author Matija Mazi */
public class BitstampTransaction {

  private final long date;
  private final int tid;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final int type;

  /**
   * Constructor
   *
   * @param date Unix timestamp date and time
   * @param tid Transaction id
   * @param price BTC price
   * @param amount BTC amount
   */
  public BitstampTransaction(
      @JsonProperty("date") long date,
      @JsonProperty("tid") int tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("type") int type) {

    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.type = type;
  }

  public int getTid() {

    return tid;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public int getType() {

    return type;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("BitstampTransaction [date=");
    builder.append(date);
    builder.append(", tid=");
    builder.append(tid);
    builder.append(", price=");
    builder.append(price);
    builder.append(", amount=");
    builder.append(amount);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }
}
