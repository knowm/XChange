package org.knowm.xchange.taurus.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.utils.jackson.UnixTimestampDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Matija Mazi
 */
public class TaurusTransaction {

  private final Date date;
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
  public TaurusTransaction(@JsonProperty("date") @JsonDeserialize(using = UnixTimestampDeserializer.class) Date date, @JsonProperty("tid") int tid,
      @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {
    this.date = date;
    this.tid = tid;
    this.price = price;
    this.amount = amount;
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

  public Date getDate() {
    return date;
  }

  @Override
  public String toString() {
    return "Transaction [date=" + date + ", tid=" + tid + ", price=" + price + ", amount=" + amount + "]";
  }
}
