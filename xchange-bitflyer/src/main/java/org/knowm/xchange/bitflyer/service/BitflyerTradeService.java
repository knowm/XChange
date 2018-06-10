package org.knowm.xchange.bitflyer.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.TradeService;

public class BitflyerTradeService extends BitflyerTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerTradeService(Exchange exchange) {
    super(exchange);
  }
}
