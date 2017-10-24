package org.knowm.xchange.dsx.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public final class DSXTrade {

  private final BigDecimal amount;
  private final BigDecimal price;
  private final long date;
  private final long tid;
  private final String tradeType;

  public DSXTrade(@JsonProperty("amount") BigDecimal amount, @JsonProperty("price") BigDecimal price,
      @JsonProperty("timestamp") long date, @JsonProperty("tid") long tid,
      @JsonProperty("type") String tradeType) {

    this.amount = amount;
    this.price = price;
    this.date = date;
    this.tid = tid;
    this.tradeType = tradeType;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public long getDate() {

    return date;
  }

  public long getTid() {

    return tid;
  }

  public String getTradeType() {

    return tradeType;
  }

  @Override
  public String toString() {

    return "DSXTrade{" +
        "amount=" + amount +
        ", price=" + price +
        ", timestamp=" + date +
        ", tid=" + tid +
        ", tradeType='" + tradeType + '\'' +
        '}';
  }

}
