package org.knowm.xchange.liqui.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiquiAccountRights {

  private final boolean info;
  private final boolean trade;
  private final boolean withdraw;

  public LiquiAccountRights(
      @JsonProperty("info") final boolean info,
      @JsonProperty("trade") final boolean trade,
      @JsonProperty("withdraw") final boolean withdraw) {
    this.info = info;
    this.trade = trade;
    this.withdraw = withdraw;
  }

  public boolean isInfo() {
    return info;
  }

  public boolean isTrade() {
    return trade;
  }

  public boolean isWithdraw() {
    return withdraw;
  }

  @Override
  public String toString() {
    return "LiquiAccountRights{"
        + "info="
        + info
        + ", trade="
        + trade
        + ", withdraw="
        + withdraw
        + '}';
  }
}
