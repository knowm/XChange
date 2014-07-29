package com.xeiam.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.BTCChinaUtils;

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
   * @deprecated use {@link #BTCChinaBuyOrderRequest(BigDecimal, BigDecimal, String)} instead.
   */
  @Deprecated
  public BTCChinaBuyOrderRequest(BigDecimal price, BigDecimal amount) {

    super(METHOD_NAME, price, amount, BTCChinaExchange.DEFAULT_MARKET);
    params = "[" + price.toPlainString() + "," + BTCChinaUtils.truncateAmount(amount).toPlainString() + "]";
  }

  public BTCChinaBuyOrderRequest(BigDecimal price, BigDecimal amount, String market) {

    super(METHOD_NAME, price, amount, market);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaBuyOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}
