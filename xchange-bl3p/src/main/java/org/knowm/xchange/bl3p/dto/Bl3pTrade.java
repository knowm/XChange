package org.knowm.xchange.bl3p.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Bl3pTrade {

  private Date date;

  @JsonProperty("trade_id")
  private long tradeId;

  @JsonProperty("price_int")
  private long priceInt;

  @JsonProperty("amount_int")
  private long amountInt;

  public Bl3pTrade(@JsonProperty("date") long date) {
    this.date = new Date(date * 1000l);
  }

  public Date getDate() {
    return date;
  }

  public long getTradeId() {
    return tradeId;
  }

  public long getPriceInt() {
    return priceInt;
  }

  public long getAmountInt() {
    return amountInt;
  }
}
