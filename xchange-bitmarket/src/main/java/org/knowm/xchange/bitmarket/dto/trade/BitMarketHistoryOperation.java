package org.knowm.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author kfonal */
public class BitMarketHistoryOperation {

  private final long id;
  private final BigDecimal amount;
  private final String currency;
  private final long time;
  private final BigDecimal rate;
  private final BigDecimal commission;
  private final String type;

  /**
   * Constructor
   *
   * @param id
   * @param amount
   * @param currency
   * @param time
   * @param rate
   * @param commission
   * @param type
   */
  public BitMarketHistoryOperation(
      @JsonProperty("id") long id,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("time") long time,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("commission") BigDecimal commission,
      @JsonProperty("type") String type) {

    this.id = id;
    this.amount = amount;
    this.currency = currency;
    this.time = time;
    this.rate = rate;
    this.commission = commission;
    this.type = type;
  }

  public long getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public long getTime() {
    return time;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getCommission() {
    return commission;
  }

  public String getType() {
    return type;
  }
}
