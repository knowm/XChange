package org.knowm.xchange.btcturk.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author semihunaldi
 * @author mertguner updated 14.01.2019 because tid value was bigger than int
 */
public final class BTCTurkTrades {

  private final Date date;
  private final BigDecimal tid;
  private final BigDecimal price;
  private final BigDecimal amount;

  /**
   * Constructor
   *
   * @param date Unix timestamp date and time
   * @param tid Transaction id
   * @param price BTC price
   * @param amount BTC amount
   */
  public BTCTurkTrades(
      @JsonProperty("date") Date date,
      @JsonProperty("tid") BigDecimal tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {
    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
  }

  public Date getDate() {
    return date;
  }

  public BigDecimal getTid() {
    return tid;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "BTCTurkTrade [date="
        + date
        + ", tid="
        + tid
        + ", price="
        + price
        + ", amount="
        + amount
        + "]";
  }
}
