package com.xeiam.xchange.okcoin.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinOrder {

  private final long orderId;

  private final int status;

  private final String symbol;

  private final String type;

  private final BigDecimal rate;

  private final BigDecimal amount;

  private final BigDecimal dealAmount;

  private final BigDecimal avgRate;

  private final Date createDate;

  public OkCoinOrder(@JsonProperty("orders_id") final long orderId, @JsonProperty("status") final int status, @JsonProperty("symbol") final String symbol, @JsonProperty("type") final String type,
      @JsonProperty("rate") final BigDecimal rate, @JsonProperty("amount") final BigDecimal amount, @JsonProperty("deal_amount") final BigDecimal dealAmount,
      @JsonProperty("avg_rate") final BigDecimal avgRate, @JsonProperty("createDate") final Date createDate) {

    this.orderId = orderId;
    this.status = status;
    this.symbol = symbol;
    this.type = type;
    this.rate = rate;
    this.amount = amount;
    this.dealAmount = dealAmount;
    this.avgRate = avgRate;
    this.createDate = createDate;
  }

  public long getOrderId() {

    return orderId;
  }

  public int getStatus() {

    return status;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getRate() {

    return rate;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getDealAmount() {

    return dealAmount;
  }

  public BigDecimal getAvgRate() {

    return avgRate;
  }

  public Date getCreateDate() {

    return createDate;
  }

}
