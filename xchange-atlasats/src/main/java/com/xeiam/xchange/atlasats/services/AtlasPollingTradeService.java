package com.xeiam.xchange.atlasats.services;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

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
