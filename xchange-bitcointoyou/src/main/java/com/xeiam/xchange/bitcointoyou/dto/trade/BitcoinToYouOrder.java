package com.xeiam.xchange.bitcointoyou.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouOrder {

  private final String asset;
  private final String currency;
  private final Long id;
  private final String action;
  private final String status;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final BigDecimal executedPriceAverage;
  private final BigDecimal executedAmount;
  private final String dateCreated;

  public BitcoinToYouOrder(@JsonProperty("asset") String asset, @JsonProperty("currency") String currency, @JsonProperty("id") Long id,
      @JsonProperty("action") String action, @JsonProperty("status") String status, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("executedPriceAverage") BigDecimal executedPriceAverage,
      @JsonProperty("executedAmount") BigDecimal executedAmount, @JsonProperty("dateCreated") String dateCreated) {

    this.asset = asset;
    this.currency = currency;
    this.id = id;
    this.action = action;
    this.status = status;
    this.price = price;
    this.amount = amount;
    this.executedPriceAverage = executedPriceAverage;
    this.executedAmount = executedAmount;
    this.dateCreated = dateCreated;
  }

  @Override
  public String toString() {

    return "BitcoinToYouOrder [" + "asset='" + asset + '\'' + ", currency='" + currency + '\'' + ", id=" + id + ", action='" + action + '\''
        + ", status='" + status + '\'' + ", price=" + price + ", amount=" + amount + ", executedPriceAverage=" + executedPriceAverage
        + ", executedAmount=" + executedAmount + ", dateCreated='" + dateCreated + '\'' + ']';
  }

  public String getAsset() {

    return asset;
  }

  public String getCurrency() {

    return currency;
  }

  public Long getId() {

    return id;
  }

  public String getAction() {

    return action;
  }

  public String getStatus() {

    return status;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getExecutedPriceAverage() {

    return executedPriceAverage;
  }

  public BigDecimal getExecutedAmount() {

    return executedAmount;
  }

  public String getDateCreated() {

    return dateCreated;
  }
}
