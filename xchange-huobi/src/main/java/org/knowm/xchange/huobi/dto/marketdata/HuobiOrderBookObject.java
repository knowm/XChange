package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiOrderBookObject {

  private final BigDecimal price;
  private final BigDecimal level;
  private final BigDecimal amount;

  public HuobiOrderBookObject(@JsonProperty("price") final BigDecimal price, @JsonProperty("level") final BigDecimal level,
      @JsonProperty("amount") final BigDecimal amount) {

    this.price = price;
    this.level = level;
    this.amount = amount;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getLevel() {

    return level;
  }

  public BigDecimal getAmount() {

    return amount;
  }

}
