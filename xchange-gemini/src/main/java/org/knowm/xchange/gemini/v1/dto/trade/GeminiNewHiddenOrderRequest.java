package org.knowm.xchange.gemini.v1.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiNewHiddenOrderRequest extends GeminiNewOrderRequest {

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
  public GeminiNewHiddenOrderRequest(String nonce, String symbol, BigDecimal amount, BigDecimal price, String exchange, String side, String type) {

    super(nonce, symbol, amount, price, exchange, side, type);
    this.is_hidden = true;
  }

}
