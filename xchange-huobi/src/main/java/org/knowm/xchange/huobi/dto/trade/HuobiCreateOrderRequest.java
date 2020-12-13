package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiCreateOrderRequest {

  @JsonProperty("account-id")
  private String accountId;

  @JsonProperty("amount")
  private String amount;

  @JsonProperty("price")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String price;

  @JsonProperty("source")
  private String source;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("type")
  private String type;

  @JsonProperty("client-order-id")
  private String clOrdId;

  @JsonProperty("stop-price")
  private String stopPrice;

  @JsonProperty("operator")
  private String operator;

  public HuobiCreateOrderRequest(
      String accountId,
      String amount,
      String price,
      String symbol,
      String type,
      String clOrdId,
      String stopPrice,
      String operator) {
    this.accountId = accountId;
    this.amount = amount;
    this.price = price;
    this.symbol = symbol;
    this.type = type;
    this.source = "api";
    this.clOrdId = clOrdId;
    this.stopPrice = stopPrice;
    this.operator = operator;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getAmount() {
    return amount;
  }

  public String getPrice() {
    return price;
  }

  public String getSource() {
    return source;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getType() {
    return type;
  }

  public String getClOrdId() {
    return clOrdId;
  }

  public String getStopPrice() {
    return stopPrice;
  }

  public String getOperator() {
    return operator;
  }
}
