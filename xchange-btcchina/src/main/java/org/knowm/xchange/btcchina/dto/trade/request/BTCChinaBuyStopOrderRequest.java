package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

/**
 * Request for {@code buyStopOrder}.
 *
 * @see <a href="http://btcchina.org/api-trade-documentation-en#buystoporder">Trade API(English)</a>
 * @see <a href="http://btcchina.org/api-trade-documentation-zh#buystoporder">Trade API(Chinese)</a>
 */
public class BTCChinaBuyStopOrderRequest extends BTCChinaStopOrderRequest {

  private static final String METHOD_NAME = "buyStopOrder";

  /**
   * Construct request for {@code buyStopOrder}.
   *
   * @param stopPrice The price in quote currency to trigger the order creation. The limit/market order will be triggered immediately if the last
   * price is more than or equal to this stop price. Max 2 decimals for BTC/CNY and LTC/CNY markets. 4 decimals for LTC/BTC market. The stop price can
   * only be specified if trailing amount or trailing percentage is not specified.
   * @param price The price in quote currency to buy 1 base currency for the order to be created when the stop price is reached. Max 2 decimals for
   * BTC/CNY and LTC/CNY markets. 4 decimals for LTC/BTC market. Market order is executed by setting price to 'null'.
   * @param amount The total amount of LTC/BTC to buy for the order to be created when the stop price is reached. Supports 4 decimal places for BTC
   * and 3 decimal places for LTC.
   * @param trailingAmount The stop trailing amount used to determine the stop price. The stop price is the trailing amount more than the lowest
   * market price seen after the creation of the order. Can only be specified if stop price or trailing percentage is not specified.
   * @param trailingPercentage The stop trailing percentage used to determine the stop price. The stop price is the trailing percentage more than the
   * lowest market price seen after the creation of the order. Can only be specified if stop price or trailing percentage is not specified.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   */
  public BTCChinaBuyStopOrderRequest(BigDecimal stopPrice, BigDecimal price, BigDecimal amount, BigDecimal trailingAmount,
      BigDecimal trailingPercentage, String market) {

    super(METHOD_NAME, stopPrice, price, amount, trailingAmount, trailingPercentage, market);
  }

}
