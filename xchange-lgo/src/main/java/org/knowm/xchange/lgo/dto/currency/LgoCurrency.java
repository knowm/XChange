package org.knowm.xchange.lgo.dto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LgoCurrency {

  private final String name;
  private final String code;
  private final int decimals;

  public LgoCurrency(
      @JsonProperty("name") String name,
      @JsonProperty("code") String code,
      @JsonProperty("decimals") int decimals) {
    this.name = name;
    this.code = code;
    this.decimals = decimals;
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public int getDecimals() {
    return decimals;
  }
}
