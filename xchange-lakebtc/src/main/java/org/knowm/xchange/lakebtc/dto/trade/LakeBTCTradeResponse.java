package org.knowm.xchange.lakebtc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCTradeResponse {

  private final String type;
  private final String currency;
  private final BigDecimal amount;
  private final BigDecimal total;
  private final long at;
  private final String id;

  public LakeBTCTradeResponse(
      @JsonProperty("type") String type,
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("total") BigDecimal total,
      @JsonProperty("at") long at) {
    this.type = type;
    this.currency = currency;
    this.amount = amount;
    this.total = total;
    this.at = at;
    this.id = total.toString() + "_" + at;
  }

  public String getType() {
    return type;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public long getAt() {
    return at;
  }

  public String getId() {
    return id;
  }
}
