package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexReplaceOrderRequest extends BitfinexNewOrderRequest {

  @JsonProperty("order_id")
  protected long replaceOrderId;

  @JsonProperty("use_remaining")
  protected boolean useRemaining = false;

  public BitfinexReplaceOrderRequest(String nonce, long replaceOrderId, String symbol, BigDecimal amount, BigDecimal price, String exchange,
      String side, String type, boolean isHidden, boolean isPostOnly, boolean useRemaining) {

    super(nonce, symbol, amount, price, exchange, side, type, isHidden, isPostOnly);

    request = "/v1/order/cancel/replace";
    this.replaceOrderId = replaceOrderId;
    this.useRemaining = useRemaining;
  }
}
