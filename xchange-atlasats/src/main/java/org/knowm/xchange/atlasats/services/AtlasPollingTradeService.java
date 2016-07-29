package org.knowm.xchange.atlasats.services;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.ExchangeException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.NotAvailableFromExchangeException;
import org.knowm.xchange.NotYetImplementedForExchangeException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.polling.BasePollingExchangeService;
import org.knowm.xchange.service.polling.PollingTradeService;
import org.knowm.xchange.service.polling.trade.TradeHistoryParams;

public class AtlasPollingTradeService extends BasePollingExchangeService implements PollingTradeService {

  public AtlasPollingTradeService(Exchange exchange) {

    super(exchange);
    // TODO Auto-generated constructor stub
  }

  @Override
  public OpenOrders getOpenOrders() throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean cancelOrder(String orderId) throws  IOException {

    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    // TODO Auto-generated method stub
    return null;
  }

}
