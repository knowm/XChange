package org.knowm.xchange.bitfinex.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitfinexNewHiddenOrderRequest extends BitfinexNewOrderRequest {

  @JsonProperty("is_hidden")
  protected boolean is_hidden;

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
  public BitfinexNewHiddenOrderRequest(String nonce, String symbol, BigDecimal amount, BigDecimal price, String exchange, String side, String type) {

    super(nonce, symbol, amount, price, exchange, side, type);
    this.is_hidden = true;
  }

}
