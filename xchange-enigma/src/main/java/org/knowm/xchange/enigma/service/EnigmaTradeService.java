package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.TradeService;

public class EnigmaTradeService extends EnigmaTradeServiceRaw implements TradeService {

  public EnigmaTradeService(Exchange exchange) {
    super(exchange);
  }
}
