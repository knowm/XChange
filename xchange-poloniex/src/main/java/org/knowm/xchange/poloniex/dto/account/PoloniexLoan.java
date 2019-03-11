package org.knowm.xchange.poloniex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author anw */
public class PoloniexLoan {

  private String id;

  private String currency;

  private BigDecimal rate;

  private BigDecimal amount;

  private int range;

  private boolean autoRenew;

  private String date;

  private BigDecimal fees;

  public PoloniexLoan(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("range") int range,
      @JsonProperty("autoRenew") boolean autoRenew,
      @JsonProperty("date") String date,
      @JsonProperty("fees") BigDecimal fees) {
    this.id = id;
    this.currency = currency;
    this.rate = rate;
    this.amount = amount;
    this.range = range;
    this.autoRenew = autoRenew;
    this.date = date;
    this.fees = fees;
  }

  public String getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public int getRange() {
    return range;
  }

  public boolean isAutoRenew() {
    return autoRenew;
  }

  public String getDate() {
    return date;
  }

  public BigDecimal getFees() {
    return fees;
  }
}
