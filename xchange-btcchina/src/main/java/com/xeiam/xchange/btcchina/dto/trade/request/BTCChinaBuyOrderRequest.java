package com.xeiam.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

/**
 * @author David Yam
 */
public final class BTCChinaBuyOrderRequest extends BTCChinaOrderRequest {

  private static final String METHOD_NAME = "buyOrder2";

  /**
   * Constructor
   *
   * @param price
   * @param amount
   * @param market
   */
  public BTCChinaBuyOrderRequest(BigDecimal price, BigDecimal amount, String market) {

    super(METHOD_NAME, price, amount, market);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaBuyOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}
