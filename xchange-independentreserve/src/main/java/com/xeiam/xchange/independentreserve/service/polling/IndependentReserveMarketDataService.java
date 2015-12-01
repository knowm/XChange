package com.xeiam.xchange.independentreserve.service.polling;

import java.io.IOException;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.independentreserve.IndependentReserveAdapters;
import com.xeiam.xchange.independentreserve.IndependentReserveExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Author: Kamil Zbikowski Date: 4/9/15
 */
public class IndependentReserveMarketDataService extends IndependentReserveMarketDataServiceRaw implements PollingMarketDataService {
  public IndependentReserveMarketDataService(IndependentReserveExchange independentReserveExchange) {
    super(independentReserveExchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return IndependentReserveAdapters.adaptOrderBook(getIndependentReserveOrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new UnsupportedOperationException();
  }
}
