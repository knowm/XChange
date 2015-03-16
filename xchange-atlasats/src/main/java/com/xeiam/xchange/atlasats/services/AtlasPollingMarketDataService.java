package com.xeiam.xchange.atlasats.services;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class AtlasPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  public AtlasPollingMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws  IOException {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    // TODO Auto-generated method stub
    return null;
  }

}
