package org.knowm.xchange.okcoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class OkCoinOrder {

  private final long orderId;

  private final int status;

  private final String symbol;

  private final String type;

  private final BigDecimal amount;

  private final BigDecimal dealAmount;

  private final Date createDate;

  private final BigDecimal price;

  private final BigDecimal averagePrice;

  public OkCoinOrder(
      @JsonProperty("order_id") final long orderId,
      @JsonProperty("status") final int status,
      @JsonProperty("symbol") final String symbol,
      @JsonProperty("type") final String type,
      @JsonProperty("price") final BigDecimal price,
      @JsonProperty("avg_price") final BigDecimal averagePrice,
      @JsonProperty("amount") final BigDecimal amount,
      @JsonProperty("deal_amount") final BigDecimal dealAmount,
      @JsonProperty("create_date") final Date createDate) {

    this.orderId = orderId;
    this.status = status;
    this.symbol = symbol;
    this.type = type;
    this.amount = amount;
    this.dealAmount = dealAmount;
    this.price = price;
    this.averagePrice = averagePrice;
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

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getDealAmount() {

    return dealAmount;
  }

  public Date getCreateDate() {

    return createDate;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAveragePrice() {

    return averagePrice;
  }
}
