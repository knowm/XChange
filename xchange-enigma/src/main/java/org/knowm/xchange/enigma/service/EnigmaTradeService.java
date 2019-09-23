package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.enigma.EnigmaAdapters;
import org.knowm.xchange.enigma.model.EnigmaException;
import org.knowm.xchange.service.trade.TradeService;

import java.io.IOException;

public class EnigmaTradeService extends EnigmaTradeServiceRaw implements TradeService {

  public EnigmaTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, EnigmaException {
    return cancelOrder(Integer.parseInt(orderId)).isResult();
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return EnigmaAdapters.adaptOpenOrders(super.openOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return String.valueOf(super.placeMarketOrderRequest(marketOrder).getId());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return String.valueOf(super.placeLimitOrderRequest(limitOrder).getId());
  }
}
