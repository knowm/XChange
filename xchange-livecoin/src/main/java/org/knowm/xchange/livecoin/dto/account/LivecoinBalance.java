package org.knowm.xchange.livecoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author walec51 */
public class LivecoinBalance {

  private final String type;
  private final String currency;
  private final BigDecimal value;

  public LivecoinBalance(
      @JsonProperty("type") String type,
      @JsonProperty("currency") String currency,
      @JsonProperty("value") BigDecimal value) {
    this.type = type;
    this.currency = currency;
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getValue() {
    return value;
  }
}
