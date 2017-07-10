package org.knowm.xchange.jubi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.jubi.JubiAdapters;
import org.knowm.xchange.jubi.dto.marketdata.JubiTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class JubiMarketDataService extends JubiMarketDataServiceRaw implements MarketDataService {

  public JubiMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
          throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    // Request data
    JubiTicker jubiTicker = getJubiTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    // Adapt to XChange DTOs
    return jubiTicker != null ? JubiAdapters.adaptTicker(jubiTicker, currencyPair) : null;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return JubiAdapters.adaptTrades(getJubiTrades(currencyPair, args), currencyPair);
  }
}
