package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GeminiNewOrderRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("symbol")
  protected String symbol;

  @JsonProperty("exchange")
  protected String exchange;

  @JsonProperty("side")
  protected String side;

  @JsonProperty("type")
  protected String type;

  @JsonProperty("amount")
  protected BigDecimal amount;

  @JsonProperty("price")
  protected BigDecimal price;

  @JsonProperty("options")
  protected Object[] options;

  /**
   * Constructor
   *
   * @param nonce
   * @param symbol
   * @param amount
   * @param price
   * @param exchange
   * @param side
   * @param type
   */
  public GeminiNewOrderRequest(
      String nonce,
      String symbol,
      BigDecimal amount,
      BigDecimal price,
      String exchange,
      String side,
      String type,
      Object[] options) {

    this.request = "/v1/order/new";
    this.nonce = nonce;
    this.symbol = symbol;
    this.amount = amount;
    this.price = price;
    this.exchange = exchange;
    this.side = side;
    this.type = type;
    this.options = options;
  }

  public String getRequest() {

    return request;
  }

  public void setRequest(String request) {

    this.request = request;
  }

  public String getNonce() {

    return nonce;
  }

  public void setNonce(String nonce) {

    this.nonce = nonce;
  }

  public String getSide() {

    return side;
  }

  public String getType() {

    return type;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getAmount() {

    return amount.toPlainString();
  }

  public String getPrice() {

    return price.toPlainString();
  }

  @JsonInclude(Include.NON_NULL)
  public Object[] getOptions() {

    return options;
  }
}
