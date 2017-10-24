package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

/**
 * Request for {@code buyStopOrder} and {@code sellStopOrder}.
 *
 * @see <a href="http://btcchina.org/api-trade-documentation-en#stop_order_api_methods">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#stop_order_api_methods">Trade API(Chinese)</a>
 */
public class BTCChinaStopOrderRequest extends BTCChinaRequest {

  public BTCChinaStopOrderRequest(String method, BigDecimal stopPrice, BigDecimal price, BigDecimal amount, BigDecimal trailingAmount,
      BigDecimal trailingPercentage, String market) {

    this.method = method;
    params = String.format("[%s,%s,%s,%s,%s,%s]", stopPrice == null ? "" : stopPrice.stripTrailingZeros().toPlainString(),
        price == null ? "null" : price.stripTrailingZeros().toPlainString(), amount.stripTrailingZeros().toPlainString(),
        trailingAmount == null ? "" : trailingAmount.stripTrailingZeros().toPlainString(),
        trailingPercentage == null ? "" : trailingPercentage.stripTrailingZeros().toPlainString(), market == null ? "" : market);
  }

}
