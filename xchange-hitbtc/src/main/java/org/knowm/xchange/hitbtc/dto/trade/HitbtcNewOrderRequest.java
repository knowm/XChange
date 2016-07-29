package org.knowm.xchange.hitbtc.dto.trade;

import java.math.BigDecimal;

import javax.ws.rs.FormParam;

public class HitbtcNewOrderRequest {

  @FormParam("clientOrderId")
  protected String clientOrderId;

  @FormParam("symbol")
  protected String symbol;

  @FormParam("side")
  protected String side;

  @FormParam("price")
  protected BigDecimal price;

  @FormParam("quantity")
  protected int quantity;

  @FormParam("timeInForce")
  protected String timeInForce;

  public HitbtcNewOrderRequest(String clientOrderId, String symbol, String side, BigDecimal price, int quantity, String timeInForce) {

    super();
    this.clientOrderId = clientOrderId;
    this.symbol = symbol;
    this.side = side;
    this.price = price;
    this.quantity = quantity;
    this.timeInForce = timeInForce;
  }

  public String getClientOrderId() {

    return clientOrderId;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getSide() {

    return side;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public int getQuantity() {

    return quantity;
  }

  public String getTimeInForce() {

    return timeInForce;
  }

}
