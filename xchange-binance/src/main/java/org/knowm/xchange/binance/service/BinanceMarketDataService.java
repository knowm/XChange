package org.knowm.xchange.binance.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderBook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BinanceMarketDataService extends BinanceMarketDataServiceRaw implements MarketDataService {

  public BinanceMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException,  IOException {
    BinanceTicker ticker = getBinanceTicker(currencyPair);
    return BinanceAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    BinanceOrderBook orderBook = getBinanceOrderBook(currencyPair);
    return BinanceAdapters.adaptOrderBook(orderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException, NotYetImplementedForExchangeException {
    throw new NotYetImplementedForExchangeException();
  }

}
