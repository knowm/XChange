package org.knowm.xchange.btctrade.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCTradeTrade {

  private final String tradeId;
  private final BigDecimal amount;
  private final BigDecimal price;
  private final String datetime;
  private final BigDecimal fee;

  public BTCTradeTrade(
      @JsonProperty("trade_id") String tradeId,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("datetime") String datetime,
      @JsonProperty("fee") BigDecimal fee) {

    this.tradeId = tradeId;
    this.amount = amount;
    this.price = price;
    this.datetime = datetime;
    this.fee = fee;
  }

  public String getTradeId() {

    return tradeId;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getDatetime() {

    return datetime;
  }

  public BigDecimal getFee() {

    return fee;
  }
}
