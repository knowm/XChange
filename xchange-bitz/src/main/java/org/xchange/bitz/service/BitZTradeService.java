package org.xchange.bitz.service;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BitZTradeService extends BitZTradeServiceRaw implements TradeService {

  public BitZTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotAvailableFromExchangeException();
  }
}
