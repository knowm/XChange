package com.xeiam.xchange.jubi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.jubi.JubiAdapters;
import com.xeiam.xchange.jubi.dto.marketdata.JubiTicker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Created by Yingzhe on 3/17/2015.
 */
public class JubiMarketDataService extends JubiMarketDataServiceRaw implements PollingMarketDataService {

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
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }
}
