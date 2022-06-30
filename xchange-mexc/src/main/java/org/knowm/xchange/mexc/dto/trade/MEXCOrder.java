package org.knowm.xchange.mexc.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MEXCOrder {

  private final String id;
  private final String symbol;
  private final String price;
  private final String quantity;
  private final String state;
  private final String type;
  private final String dealQuantity;
  private final String dealAmount;
  private final String createTime;

  @JsonCreator
  public MEXCOrder(
          @JsonProperty("id") String id,
          @JsonProperty("symbol") String symbol,
          @JsonProperty("price") String price,
          @JsonProperty("quantity") String quantity,
          @JsonProperty("state") String state,
          @JsonProperty("type") String type,
          @JsonProperty("deal_quantity") String dealQuantity,
          @JsonProperty("deal_amount") String dealAmount,
          @JsonProperty("create_time") String createTime) {
    this.id = id;
    this.symbol = symbol;
    this.price = price;
    this.quantity = quantity;
    this.state = state;
    this.type = type;
    this.dealQuantity = dealQuantity;
    this.dealAmount = dealAmount;
    this.createTime = createTime;
  }

  public String getId() {
    return id;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getPrice() {
    return price;
  }

  public String getQuantity() {
    return quantity;
  }

  public String getState() {
    return state;
  }

  public String getType() {
    return type;
  }

  public String getDealQuantity() {
    return dealQuantity;
  }

  public String getDealAmount() {
    return dealAmount;
  }

  public String getCreateTime() {
    return createTime;
  }
}


