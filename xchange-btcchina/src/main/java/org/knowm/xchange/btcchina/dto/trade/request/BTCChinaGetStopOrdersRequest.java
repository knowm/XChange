package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

/**
 * Request for {@code getStopOrders}.
 *
 * @see <a href="http://btcchina.org/api-trade-documentation-en#getstoporders">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#getstoporders">Trade API(Chinese)</a>
 */
public class BTCChinaGetStopOrdersRequest extends BTCChinaRequest {

  private static final String METHOD_NAME = "getStopOrders";

  /**
   * Constructs a request for {@code getStopOrders}.
   *
   * @param status Status to filter on: [ open | closed | cancelled | error ]
   * @param type Type to filter on: [ ask | bid ]
   * @param stopPrice Price to filter on. For bid stop orders, will return all stop orders less than or equal this stop price. For ask stop orders,
   * will return all stop orders greater than or equal to this stop price.
   * @param limit Limit the number of stop orders, default value is 1000.
   * @param offset Start index used for pagination, default value is 0.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   */
  public BTCChinaGetStopOrdersRequest(String status, String type, BigDecimal stopPrice, Integer limit, Integer offset, String market) {

    this.method = METHOD_NAME;
    this.params = String.format("[%s,%s,%s,%d,%d,%s]", status == null ? "" : status, type == null ? "" : type,
        stopPrice == null ? "" : stopPrice.stripTrailingZeros().toPlainString(), limit == null ? 1000 : limit.intValue(),
        offset == null ? 0 : offset.intValue(), market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

}
