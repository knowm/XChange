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

  public HuobiCreateOrderRequest(
      String accountId, String amount, String price, String symbol, String type) {
    this.accountId = accountId;
    this.amount = amount;
    this.price = price;
    this.symbol = symbol;
    this.type = type;
    source = "api";
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
}
