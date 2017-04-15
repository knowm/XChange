package org.knowm.xchange.btcchina.dto.trade.request;

/**
 * Request for {@code getStopOrder}.
 *
 * @see <a href="http://btcchina.org/api-trade-documentation-en#getstoporder">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#getstoporder">Trade API(Chinese)</a>
 */
public class BTCChinaGetStopOrderRequest extends BTCChinaGetOrderRequest {

  private static final String METHOD_NAME = "getStopOrder";

  /**
   * Construct a request for {@code getStopOrder}.
   *
   * @param id The stop order id.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   */
  public BTCChinaGetStopOrderRequest(int id, String market) {

    super(METHOD_NAME, id, market);
  }

}
