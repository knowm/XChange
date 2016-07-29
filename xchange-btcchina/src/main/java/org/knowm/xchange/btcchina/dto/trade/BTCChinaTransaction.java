package org.knowm.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaTransaction {

  private final long id;
  private final String type;
  private final BigDecimal btcAmount;
  private final BigDecimal ltcAmount;
  private final BigDecimal cnyAmount;
  private final long date;
  private final String market;

  public BTCChinaTransaction(@JsonProperty("id") long id, @JsonProperty("type") String type, @JsonProperty("btc_amount") BigDecimal btcAmount,
      @JsonProperty("ltc_amount") BigDecimal ltcAmount, @JsonProperty("cny_amount") BigDecimal cnyAmount, @JsonProperty("date") long date,
      @JsonProperty("market") String market) {

    this.id = id;
    this.type = type;
    this.btcAmount = btcAmount;
    this.ltcAmount = ltcAmount;
    this.cnyAmount = cnyAmount;
    this.date = date;
    this.market = market;
  }

  public long getId() {

    return id;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getBtcAmount() {

    return btcAmount;
  }

  public BigDecimal getCnyAmount() {

    return cnyAmount;
  }

  public BigDecimal getLtcAmount() {

    return ltcAmount;
  }

  public long getDate() {

    return date;
  }

  public String getMarket() {

    return market;
  }

}
