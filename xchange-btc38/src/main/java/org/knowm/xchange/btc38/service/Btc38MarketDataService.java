package org.knowm.xchange.btc38.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btc38.Btc38Adapters;
import org.knowm.xchange.btc38.dto.marketdata.Btc38Ticker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

/**
 * Created by Yingzhe on 12/19/2014.
 */
public class Btc38MarketDataService extends Btc38MarketDataServiceRaw implements MarketDataService {

  public Btc38MarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    // Request data
    Btc38Ticker btc38Ticker = getBtc38Ticker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    // Adapt to XChange DTOs
    return btc38Ticker != null ? Btc38Adapters.adaptTicker(btc38Ticker, currencyPair) : null;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return Btc38Adapters.adaptTrades(getBtc38Trades(currencyPair, args), currencyPair);
  }
}
