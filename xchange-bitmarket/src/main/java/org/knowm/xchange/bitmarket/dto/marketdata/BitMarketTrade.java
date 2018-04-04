package org.knowm.xchange.bitmarket.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author kpysniak */
public class BitMarketTrade {

  private final String tid;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final long date;
  private final String type;

  /**
   * Constructor
   *
   * @param tid
   * @param price
   * @param amount
   * @param date
   * @param type
   */
  public BitMarketTrade(
      @JsonProperty("tid") String tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("date") long date,
      @JsonProperty("type") String type) {

    this.tid = tid;
    this.price = price;
    this.amount = amount;
    this.date = date;
    this.type = type;
  }

  public String getTid() {

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

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "BitMarketTrade [tid="
        + tid
        + ", price="
        + price
        + ", amount="
        + amount
        + ", date="
        + date
        + ", type="
        + type
        + "]";
  }
}
