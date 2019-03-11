package org.knowm.xchange.okcoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class OkCoinTrade {

  private final Date date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final long tid;
  private final String type;

  /**
   * Constructor
   *
   * @param date
   * @param price
   * @param amount
   * @param tid
   * @param type
   */
  public OkCoinTrade(
      @JsonProperty("date") final long date,
      @JsonProperty("price") final BigDecimal price,
      @JsonProperty("amount") final BigDecimal amount,
      @JsonProperty("tid") final long tid,
      @JsonProperty("type") final String type) {

    this.date = new Date(date * 1000);
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.type = type;
  }

  public Date getDate() {

    return date;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public long getTid() {

    return tid;
  }

  public String getType() {

    return type;
  }

  @Override
  public String toString() {

    return "OkCoinTrade [date="
        + date
        + ", price="
        + price
        + ", amount="
        + amount
        + ", tid="
        + tid
        + ", type="
        + type
        + "]";
  }
}
