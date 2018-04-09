package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class OkCoinFuturesOrder {

  private final BigDecimal amount;
  private final String contractName;
  private final Date createdDate;
  private final BigDecimal dealAmount;
  private final BigDecimal fee;
  private final String orderId;
  private final BigDecimal price;
  private final BigDecimal avgPrice;
  private final int status;
  private final String symbol;
  private final String type;
  private final BigDecimal unitAmount;
  private final int leverRate;

  public OkCoinFuturesOrder(
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("contract_name") String contractName,
      @JsonProperty("create_date") Date createdDate,
      @JsonProperty("deal_amount") BigDecimal dealAmount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("price_avg") BigDecimal avgPrice,
      @JsonProperty("status") int status,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("type") String type,
      @JsonProperty("unit_amount") BigDecimal unitAmount,
      @JsonProperty("lever_rate") int leverRate) {

    this.amount = amount;
    this.contractName = contractName;
    this.createdDate = createdDate;
    this.dealAmount = dealAmount;
    this.fee = fee;
    this.orderId = orderId;
    this.price = price;
    this.avgPrice = avgPrice;
    this.status = status;
    this.symbol = symbol;
    this.type = type;
    this.unitAmount = unitAmount;
    this.leverRate = leverRate;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public String getContractName() {

    return contractName;
  }

  public Date getCreatedDate() {

    return createdDate;
  }

  public BigDecimal getDealAmount() {

    return dealAmount;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getOrderId() {

    return orderId;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAvgPrice() {

    return avgPrice;
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

  public BigDecimal getUnitAmount() {

    return unitAmount;
  }

  public int getLeverRate() {

    return leverRate;
  }
}
