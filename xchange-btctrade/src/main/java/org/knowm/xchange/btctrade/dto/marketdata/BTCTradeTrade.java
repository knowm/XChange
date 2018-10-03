package org.knowm.xchange.btctrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCTradeTrade {

  private final long date;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final long tid;
  private final String type;

  public BTCTradeTrade(
      @JsonProperty("date") long date,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("tid") long tid,
      @JsonProperty("type") String type) {

    this.date = date;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.type = type;
  }

  public long getDate() {

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
}
