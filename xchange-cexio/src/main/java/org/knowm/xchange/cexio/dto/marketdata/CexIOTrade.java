package org.knowm.xchange.cexio.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox Since: 2/5/14
 */
public class CexIOTrade {

  private final BigDecimal amount;
  private final BigDecimal price;
  private final long date;
  private final long tid;
  private final String type;

  /**
   * Constructor
   *
   * @param amount
   * @param price
   * @param date
   * @param tid
   */
  public CexIOTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price, @JsonProperty("date") long date,
      @JsonProperty("tid") long tid, @JsonProperty("type") String type) {

    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.date = date;
    this.type = type;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getTid() {

    return tid;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("CexIOTrade [amount=");
    builder.append(amount);
    builder.append(", price=");
    builder.append(price);
    builder.append(", date=");
    builder.append(date);
    builder.append(", tid=");
    builder.append(tid);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
