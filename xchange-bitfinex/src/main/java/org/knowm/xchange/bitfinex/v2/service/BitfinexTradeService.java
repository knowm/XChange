package org.knowm.xchange.bitfinex.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.service.BitfinexTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class BitfinexTradeService extends BitfinexTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexTradeService(Exchange exchange) {
    super(exchange);
  }
}
