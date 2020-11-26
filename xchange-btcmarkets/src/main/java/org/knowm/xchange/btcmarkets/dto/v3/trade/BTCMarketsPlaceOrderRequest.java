package org.knowm.xchange.btcmarkets.dto.v3.trade;

public class BTCMarketsPlaceOrderRequest {

  public final String marketId;
  public final String price;
  public final String amount;
  public final String type;
  public final String side;
  public final String triggerPrice;
  public final String targetAmount;
  public final String timeInForce;
  public final String postOnly;
  public final String selfTrade;
  public final String clientOrderId;

  public BTCMarketsPlaceOrderRequest(
      String marketId,
      String price,
      String amount,
      String type,
      String side,
      String triggerPrice,
      String targetAmount,
      String timeInForce,
      String postOnly,
      String selfTrade,
      String clientOrderId) {
    this.marketId = marketId;
    this.price = price;
    this.amount = amount;
    this.type = type;
    this.side = side;
    this.triggerPrice = triggerPrice;
    this.targetAmount = targetAmount;
    this.timeInForce = timeInForce;
    this.postOnly = postOnly;
    this.selfTrade = selfTrade;
    this.clientOrderId = clientOrderId;
  }
}
