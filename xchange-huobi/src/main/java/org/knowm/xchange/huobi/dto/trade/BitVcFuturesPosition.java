package org.knowm.xchange.huobi.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcFuturesPosition {
  private final int id;
  private final int tradeType;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final double holdProfitLoss;
  private final double usedMargin;
  private final int storeId;

  public BitVcFuturesPosition(@JsonProperty("id") final int id, @JsonProperty("tradeType") final int tradeType,
      @JsonProperty("price") final BigDecimal price, @JsonProperty("money") final BigDecimal amount,
      @JsonProperty("holdProfitLoss") final double holdProfitLoss, @JsonProperty("usedMargin") final double usedMargin,
      @JsonProperty("storeId") final int storeId) {
    this.id = id;
    this.tradeType = tradeType;
    this.price = price;
    this.amount = amount;
    this.holdProfitLoss = holdProfitLoss;
    this.usedMargin = usedMargin;
    this.storeId = storeId;
  }

  public int getTradeType() {
    return tradeType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public double getHoldProfitLoss() {
    return holdProfitLoss;
  }

  public double getUsedMargin() {
    return usedMargin;
  }

  public int getStoreId() {
    return storeId;
  }

  public int getId() {

    return id;
  }
}
