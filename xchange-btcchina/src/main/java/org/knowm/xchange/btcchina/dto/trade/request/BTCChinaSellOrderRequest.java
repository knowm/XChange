package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

/**
 * @author David Yam
 */
public final class BTCChinaSellOrderRequest extends BTCChinaOrderRequest {

  private static final String METHOD_NAME = "sellOrder2";

  public BTCChinaSellOrderRequest(BigDecimal price, BigDecimal amount, String market) {

    super(METHOD_NAME, price, amount, market);
  }

  @Override
  public String toString() {

    return String.format("BTCChinaSellOrderRequest{id=%d, method=%s, params=%s}", id, method, params);
  }

}
