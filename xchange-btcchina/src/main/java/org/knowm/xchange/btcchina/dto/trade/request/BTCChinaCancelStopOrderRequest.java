package org.knowm.xchange.btcchina.dto.trade.request;

/**
 * Request for {@code cancelStopOrder}.
 *
 * @see <a href="http://btcchina.org/api-trade-documentation-en#cancelstoporder">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#cancelstoporder">Trade API(Chinese)</a>
 */
public class BTCChinaCancelStopOrderRequest extends BTCChinaCancelOrderRequest {

  private static final String METHOD_NAME = "cancelStopOrder";

  /**
   * Construct a request for {@code cancelStopOrder}.
   *
   * @param id The stop order id to cancel.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   */
  public BTCChinaCancelStopOrderRequest(int id, String market) {

    super(METHOD_NAME, id, market);
  }

}
