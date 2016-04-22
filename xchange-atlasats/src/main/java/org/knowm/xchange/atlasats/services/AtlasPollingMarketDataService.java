package org.knowm.xchange.atlasats.services;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.ExchangeException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.NotAvailableFromExchangeException;
import org.knowm.xchange.NotYetImplementedForExchangeException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.BasePollingExchangeService;
import org.knowm.xchange.service.polling.PollingMarketDataService;

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
