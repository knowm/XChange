package org.knowm.xchange.quadrigacx.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.quadrigacx.QuadrigaCxAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class QuadrigaCxMarketDataService extends QuadrigaCxMarketDataServiceRaw implements MarketDataService {

  public QuadrigaCxMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return QuadrigaCxAdapters.adaptTicker(getQuadrigaCxTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return QuadrigaCxAdapters.adaptOrderBook(getQuadrigaCxOrderBook(currencyPair), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return QuadrigaCxAdapters.adaptTrades(getQuadrigaCxTransactions(currencyPair, args), currencyPair);
  }
}
