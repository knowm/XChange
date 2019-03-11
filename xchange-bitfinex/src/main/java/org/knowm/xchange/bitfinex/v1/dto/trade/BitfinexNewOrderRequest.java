package org.knowm.xchange.bitfinex.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BitfinexNewOrderRequest {

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
  protected String amount;

  @JsonProperty("price")
  protected String price;

  @JsonProperty("is_hidden")
  protected boolean is_hidden = false;

  @JsonProperty("is_postonly")
  protected boolean is_postonly = false;

  @JsonProperty("ocoorder")
  protected boolean ocoorder = false;

  @JsonProperty("buy_price_oco")
  protected String buy_price_oco;

  @JsonProperty("sell_price_oco")
  protected String sell_price_oco;

  public BitfinexNewOrderRequest(
      String nonce,
      String symbol,
      BigDecimal amount,
      BigDecimal price,
      String exchange,
      String side,
      String type,
      BigDecimal ocoAmount) {

    this.request = "/v1/order/new";
    this.nonce = nonce;
    this.symbol = symbol;
    if (amount != null) {
      this.amount = amount.toPlainString();
    }
    if (price != null) {
      this.price = price.toPlainString();
    }
    this.exchange = exchange;
    this.side = side;
    this.type = type;
    if (ocoAmount != null) {
      ocoorder = true;
      if (side.equals("sell")) {
        sell_price_oco = ocoAmount.toPlainString();
      } else {
        buy_price_oco = ocoAmount.toPlainString();
      }
    }
  }

  public BitfinexNewOrderRequest(
      String nonce,
      String symbol,
      BigDecimal amount,
      BigDecimal price,
      String exchange,
      String side,
      String type,
      boolean isHidden,
      boolean isPostOnly,
      BigDecimal ocoAmount) {

    this(nonce, symbol, amount, price, exchange, side, type, ocoAmount);
    this.is_hidden = isHidden;
    this.is_postonly = isPostOnly;
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
    if (amount == null) {
      return null;
    }

    return amount;
  }

  public String getPrice() {
    if (price == null) {
      return null;
    }
    return price;
  }
}
