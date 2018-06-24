package org.knowm.xchange.kuna.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.TradeService;

/** @author Dat Bui */
public class KunaTradeService extends KunaTradeServiceRaw implements TradeService {

  /**
   * Constructor.
   *
   * @param exchange
   */
  public KunaTradeService(Exchange exchange) {
    super(exchange);
  }
}
