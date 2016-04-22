package org.knowm.xchange.mexbt.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeXBTBalance {

  private final String name;
  private final BigDecimal balance;
  private final BigDecimal hold;
  private final long tradeCount;

  public MeXBTBalance(@JsonProperty("name") String name, @JsonProperty("balance") BigDecimal balance, @JsonProperty("hold") BigDecimal hold,
      @JsonProperty("tradeCount") long tradeCount) {
    this.name = name;
    this.balance = balance;
    this.hold = hold;
    this.tradeCount = tradeCount;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getHold() {
    return hold;
  }

  public long getTradeCount() {
    return tradeCount;
  }

}
