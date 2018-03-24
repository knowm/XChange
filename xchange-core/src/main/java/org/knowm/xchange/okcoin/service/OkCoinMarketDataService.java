package org.knowm.xchange.okcoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTrade;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class OkCoinMarketDataService extends OkCoinMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkCoinAdapters.adaptTicker(getTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkCoinAdapters.adaptOrderBook(getDepth(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    final OkCoinTrade[] trades;

    if (args == null || args.length == 0) {
      trades = getTrades(currencyPair);
    } else {
      trades = getTrades(currencyPair, (Long) args[0]);
    }
    return OkCoinAdapters.adaptTrades(trades, currencyPair);
  }
}
