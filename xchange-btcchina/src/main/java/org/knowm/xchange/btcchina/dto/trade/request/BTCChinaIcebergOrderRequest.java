package org.knowm.xchange.btcchina.dto.trade.request;

import java.math.BigDecimal;

import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.BTCChinaRequest;

public class BTCChinaIcebergOrderRequest extends BTCChinaRequest {

  /**
   * Construct a buy/sell iceberg order request.
   *
   * @param price The price in quote currency to buy 1 base currency. Max 2 decimals for BTC/CNY and LTC/CNY markets. 4 decimals for LTC/BTC market.
   * Market iceberg order is executed by setting price to 'null'.
   * @param amount The total amount of LTC/BTC to buy. Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param disclosedAmount The disclosed amount of LTC/BTC to buy. Supports 4 decimal places for BTC and 3 decimal places for LTC.
   * @param variance Default to 0. Must be less than 1. When given, used as variance to the disclosed amount when the order is created.
   * @param market Default to “BTCCNY”. [ BTCCNY | LTCCNY | LTCBTC ]
   */
  public BTCChinaIcebergOrderRequest(String method, BigDecimal price, BigDecimal amount, BigDecimal disclosedAmount, BigDecimal variance,
      String market) {

    this.method = method;
    this.params = String.format("[%1$s,%2$s,%3$s,%4$s,\"%5$s\"]", price == null ? "null" : price.stripTrailingZeros().toPlainString(),
        amount.stripTrailingZeros().toPlainString(), disclosedAmount.stripTrailingZeros().toPlainString(),
        variance.stripTrailingZeros().toPlainString(), market == null ? BTCChinaExchange.DEFAULT_MARKET : market);
  }

}
