package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.service.trade.TradeService;

public class EnigmaTradeService extends EnigmaTradeServiceRaw implements TradeService {

  public EnigmaTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return TradeService.super.placeMarketOrder(marketOrder);
  }
}
