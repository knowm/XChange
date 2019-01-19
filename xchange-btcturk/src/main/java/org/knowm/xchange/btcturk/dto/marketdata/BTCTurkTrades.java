package org.knowm.xchange.btcturk.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author semihunaldi 
 * @author  mertguner */
public final class BTCTurkTrades {

  private final long date;
  private final int tid;
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
      @JsonProperty("date") long date,
      @JsonProperty("tid") int tid,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {
    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
  }

  public long getDate() {
    return date;
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

@Override
public String toString() {
	return "BTCTurkTrades [date=" + date + ", tid=" + tid + ", price=" + price + ", amount=" + amount + "]";
}

  
}
