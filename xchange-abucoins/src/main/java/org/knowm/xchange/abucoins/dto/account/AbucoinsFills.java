package org.knowm.xchange.abucoins.dto.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpResponseAware;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /fills</code> endpoint. Example:
 * <code><pre>
 * [
 *   {
 *     "trade_id":"785705",
 *     "product_id":"BTC-PLN",
 *     "price":"14734.55000000",
 *     "size":"100.00000000",
 *     "order_id":"4196245",
 *     "created_at":"2017-09-28T13:08:43Z",
 *     "liquidity":"T",
 *     "side":"sell"
 *   },
 *   {
 *     "trade_id":"785704",
 *     "product_id":"BTC-PLN",
 *     "price":"14734.55000000",
 *     "size":"0.01000000",
 *     "order_id":"4196245",
 *     "created_at":"2017-09-28T13:08:43Z",
 *     "liquidity":"T",
 *     "side":"sell"
 *   }
 * ]
 * </pre></code>
 *
 * @author bryant_harris
 */
public class AbucoinsFills extends ArrayList<AbucoinsFill> implements HttpResponseAware {
  private Map<String, List<String>> responseHeaders;

  @Override
  public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
    this.responseHeaders = responseHeaders;
  }

  @Override
  public Map<String, List<String>> getResponseHeaders() {
    return responseHeaders;
  }
}
