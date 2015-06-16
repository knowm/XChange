package com.xeiam.xchange.bitmarket.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Yaroslav
 * Date: 24/04/15
 * Time: 15:18
 */
public class BitMarketOrder {

  /*
id - market order identifier.
market - market where the order has been made.
amount - cryptocurrency amount.
rate - exchange rate.
fiat - fiat amount after exchange.
type - order type ("buy" or "sell").
time - order creation time.
   */

  private final String id;
  private final String market;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final BigDecimal fiat;
  private final String type;
  private final Long time;

  public BitMarketOrder(@JsonProperty("id") String id,
                        @JsonProperty("market") String market,
                        @JsonProperty("amount") BigDecimal amount,
                        @JsonProperty("rate") BigDecimal rate,
                        @JsonProperty("fiat") BigDecimal fiat,
                        @JsonProperty("type") String type,
                        @JsonProperty("time") Long time) {
    this.id = id;
    this.market = market;
    this.amount = amount;
    this.rate = rate;
    this.fiat = fiat;
    this.type = type;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public String getMarket() {
    return market;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getFiat() {
    return fiat;
  }

  public String getType() {
    return type;
  }

  public Long getTime() {
    return time;
  }
}
