package org.knowm.xchange.bankera.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankeraTrade {

  private final String market;
  private final String amount;
  private final String price;
  private final String side;
  private final String time;

  public BankeraTrade(
      @JsonProperty("market") String market,
      @JsonProperty("amount") String amount,
      @JsonProperty("price") String price,
      @JsonProperty("side") String side,
      @JsonProperty("time") String time) {
    this.market = market;
    this.amount = amount;
    this.price = price;
    this.side = side;
    this.time = time;
  }

  public String getMarket() {
    return market;
  }

  public String getAmount() {
    return amount;
  }

  public String getPrice() {
    return price;
  }

  public String getSide() {
    return side;
  }

  public String getTime() {
    return time;
  }
}
