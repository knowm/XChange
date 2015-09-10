package com.xeiam.xchange.mexbt.service.polling;

import static com.xeiam.xchange.mexbt.MeXBTAdapters.adaptOrderBook;
import static com.xeiam.xchange.mexbt.MeXBTAdapters.adaptTicker;
import static com.xeiam.xchange.mexbt.MeXBTAdapters.adaptTrades;
import static com.xeiam.xchange.mexbt.MeXBTAdapters.toCurrencyPair;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class MeXBTMarketDataService extends MeXBTMarketDataServiceRaw implements PollingMarketDataService {

  public MeXBTMarketDataService(Exchange exchange) {
    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return adaptTicker(currencyPair, getTicker(toCurrencyPair(currencyPair)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {
    return adaptOrderBook(currencyPair, getOrderBook(toCurrencyPair(currencyPair)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final Long since;
    if (args.length > 0 && args[0] instanceof Number) {
      since = ((Number) args[0]).longValue();
    } else {
      since = null;
    }
    return adaptTrades(currencyPair, getTrades(toCurrencyPair(currencyPair), since));
  }

}
