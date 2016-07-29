package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiTradeObject {

  private final String time;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String type;

  public HuobiTradeObject(@JsonProperty("time") final String time, @JsonProperty("price") final BigDecimal price,
      @JsonProperty("amount") final BigDecimal amount, @JsonProperty("type") final String type) {

    this.time = time;
    this.price = price;
    this.amount = amount;
    this.type = type;
  }

  public String getTime() {

    return time;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getType() {

    return type;
  }

}
