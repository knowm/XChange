package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.model.EnigmaException;
import org.knowm.xchange.service.trade.TradeService;

public class EnigmaTradeService extends EnigmaTradeServiceRaw implements TradeService {

  public EnigmaTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, EnigmaException {
    return cancelOrder(Integer.parseInt(orderId)).isResult();
  }
}
