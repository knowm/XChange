package com.xeiam.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.BTCChinaUtils;

/**
 * @author David Yam
 */
public final class BTCChinaSellOrderRequest extends BTCChinaOrderRequest {

  private static final String METHOD_NAME = "sellOrder2";

  /**
   * Constructor
   * 
   * @param price unit price to sell at
   * @param amount number of units to sell
   * @deprecated use {@link #BTCChinaSellOrderRequest(BigDecimal, BigDecimal, String)} instead.
   */
  @Deprecated
  public BTCChinaSellOrderRequest(BigDecimal price, BigDecimal amount) {

    super(METHOD_NAME, price, amount, BTCChinaExchange.DEFAULT_MARKET);
    params = "[" + price.toPlainString() + "," + BTCChinaUtils.truncateAmount(amount).toPlainString() + "]";
  }

  public BTCChinaSellOrderRequest(BigDecimal price, BigDecimal amount, String market) {

    super(METHOD_NAME, price, amount, market);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaSellOrderRequest{id=%d, method=%s, params=%s}", id, method, params.toString());
  }

}
