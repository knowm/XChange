package org.knowm.xchange.cryptonit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Data object representing a Trade from Cryptonit */
public final class CryptonitOrder {

  private final String type;
  private final String bid_currency;
  private final String ask_currency;
  private final BigDecimal bid_amount;
  private final BigDecimal ask_amount;
  private final BigDecimal ask_rate;
  private final BigDecimal bid_rate;
  private final long created;
  private final long filled;

  /**
   * @param type
   * @param bid_currency
   * @param ask_currency
   * @param bid_amount
   * @param ask_amount
   * @param ask_rate
   * @param bid_rate
   * @param created
   * @param filled
   */
  public CryptonitOrder(
      @JsonProperty("type") String type,
      @JsonProperty("bid_currency") String bid_currency,
      @JsonProperty("ask_currency") String ask_currency,
      @JsonProperty("bid_amount") BigDecimal bid_amount,
      @JsonProperty("ask_amount") BigDecimal ask_amount,
      @JsonProperty("ask_rate") BigDecimal ask_rate,
      @JsonProperty("bid_rate") BigDecimal bid_rate,
      @JsonProperty("created") long created,
      @JsonProperty("filled") long filled) {

    this.type = type;
    this.bid_currency = bid_currency;
    this.ask_currency = ask_currency;
    this.bid_amount = bid_amount;
    this.ask_amount = ask_amount;
    this.ask_rate = ask_rate;
    this.bid_rate = bid_rate;
    this.created = created;
    this.filled = filled;
  }

  public String getType() {

    return type;
  }

  public String getBidCurrency() {

    return bid_currency;
  }

  public String getAskCurrency() {

    return ask_currency;
  }

  public BigDecimal getBidAmount() {

    return bid_amount;
  }

  public BigDecimal getAskAmount() {

    return ask_amount;
  }

  public BigDecimal getAskRate() {

    return ask_rate;
  }

  public long getCreated() {

    return created;
  }

  public long getFilled() {

    return filled;
  }

  public BigDecimal getBidRate() {

    return bid_rate;
  }

  @Override
  public String toString() {

    return "CryptonitOrder [type="
        + type
        + ", bid_currency="
        + bid_currency
        + ", ask_currency="
        + ask_currency
        + ", bid_amount="
        + bid_amount
        + ", ask_amount="
        + ask_amount
        + ", ask_rate="
        + ask_rate
        + ", created="
        + created
        + ", filled="
        + filled
        + "]";
  }
}
