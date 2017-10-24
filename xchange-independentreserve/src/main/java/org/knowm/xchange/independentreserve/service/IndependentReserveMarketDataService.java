package org.knowm.xchange.independentreserve.service;

import java.io.IOException;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.independentreserve.IndependentReserveAdapters;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Author: Kamil Zbikowski Date: 4/9/15
 */
public class IndependentReserveMarketDataService extends IndependentReserveMarketDataServiceRaw implements MarketDataService {
  public IndependentReserveMarketDataService(IndependentReserveExchange independentReserveExchange) {
    super(independentReserveExchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return IndependentReserveAdapters
        .adaptOrderBook(getIndependentReserveOrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }
}
