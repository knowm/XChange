package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Used for open orders, order history, trade history.
 */
@JsonPropertyOrder({"currency", "instrument", "limit", "since"})
public class BTCMarketsOpenOrdersAndTradeHistoryRequest {

  public final String currency;
  public final String instrument;
  public final Integer limit;
  public final Long since;

  /**
   * @param since the ascending trade id
   * @see https://github.com/BTCMarkets/API/wiki/Trading-API#order-history & https://github.com/BTCMarkets/API/wiki/Trading-API#open-orders
   */
  public BTCMarketsOpenOrdersAndTradeHistoryRequest(String currency, String instrument, Integer limit, Long since) {
    this.currency = currency;
    this.instrument = instrument;
    this.limit = limit;
    this.since = since;
  }
}
