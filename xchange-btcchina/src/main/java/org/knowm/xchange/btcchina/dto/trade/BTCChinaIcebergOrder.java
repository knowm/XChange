package org.knowm.xchange.btcchina.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCChinaIcebergOrder {

  private final int id;
  private final String type;
  private final BigDecimal price;
  private final String market;
  private final BigDecimal amount;
  private final BigDecimal amountOriginal;
  private final BigDecimal disclosedAmount;
  private final BigDecimal variance;
  private final long date;
  private final String status;
  private final BTCChinaOrder[] orders;

  public BTCChinaIcebergOrder(@JsonProperty("id") int id, @JsonProperty("type") String type, @JsonProperty("price") BigDecimal price,
      @JsonProperty("market") String market, @JsonProperty("amount") BigDecimal amount, @JsonProperty("amount_original") BigDecimal amountOriginal,
      @JsonProperty("disclosed_amount") BigDecimal disclosedAmount, @JsonProperty("variance") BigDecimal variance, @JsonProperty("date") long date,
      @JsonProperty("status") String status, @JsonProperty("order") BTCChinaOrder[] orders) {

    super();
    this.id = id;
    this.type = type;
    this.price = price;
    this.market = market;
    this.amount = amount;
    this.amountOriginal = amountOriginal;
    this.disclosedAmount = disclosedAmount;
    this.variance = variance;
    this.date = date;
    this.status = status;
    this.orders = orders;
  }

  public int getId() {

    return id;
  }

  public String getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getMarket() {

    return market;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getAmountOriginal() {

    return amountOriginal;
  }

  public BigDecimal getDisclosedAmount() {

    return disclosedAmount;
  }

  public BigDecimal getVariance() {

    return variance;
  }

  public long getDate() {

    return date;
  }

  public String getStatus() {

    return status;
  }

  public BTCChinaOrder[] getOrders() {

    return orders;
  }

}
