package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaOrderRequest extends BTCChinaRequest {

  public BTCChinaOrderRequest(String method, BigDecimal price, BigDecimal amount, String market) {

    this.method = method;
    params = String.format("[%1$s,%2$s,\"%3$s\"]", price == null ? "null" : price.stripTrailingZeros().toPlainString(),
        amount.stripTrailingZeros().toPlainString(), market);
  }

}
