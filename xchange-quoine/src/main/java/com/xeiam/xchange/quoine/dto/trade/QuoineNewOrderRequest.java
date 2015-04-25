package com.xeiam.xchange.quoine.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoineNewOrderRequest {

  //  example:
  //{
  //  order: {
  //    order_type: "limit",
  //    product_code: "CASH",
  //    currency_pair_code: "BTCJPY",
  //    side: "sell",
  //    quantity: 5.0,
  //    price: 500
  //  }
  //}

  @JsonProperty("order_type")
  private final String orderType;//  Values: limit, market or range.

  @JsonProperty("product_code")
  private final String productCode = "CASH"; //  Values: CASH

  @JsonProperty("currency_pair_code")
  private final String currencyPairCode; // Values: BTCUSD, BTCEUR, BTCJPY, BTCSGD, BTCHKD, BTCIDR, BTCAUD, BTCPHP.

  @JsonProperty("side")
  private final String side; // buy or sell

  @JsonProperty("quantity")
  private final BigDecimal quantity;//  Amount of BTC you want to trade.

  @JsonProperty("price")
  private final BigDecimal price; //  Price of BTC you want to trade.

  /**
   * Constructor
   *
   * @param orderType
   * @param currencyPairCode
   * @param side
   * @param quantity
   * @param price
   */
  public QuoineNewOrderRequest(String orderType, String currencyPairCode, String side, BigDecimal quantity, BigDecimal price) {
    this.orderType = orderType;
    this.currencyPairCode = currencyPairCode;
    this.side = side;
    this.quantity = quantity;
    this.price = price;
  }

  public String getOrderType() {
    return orderType;
  }

  public String getProductCode() {
    return productCode;
  }

  public String getCurrencyPairCode() {
    return currencyPairCode;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "QuoineNewOrderRequest [orderType=" + orderType + ", productCode=" + productCode + ", currencyPairCode=" + currencyPairCode + ", side="
        + side + ", quantity=" + quantity + ", price=" + price + "]";
  }

}
