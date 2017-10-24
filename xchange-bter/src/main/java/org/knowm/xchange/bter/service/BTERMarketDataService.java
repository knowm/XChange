package org.knowm.xchange.bter.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.BTERAdapters;
import org.knowm.xchange.bter.dto.marketdata.BTERDepth;
import org.knowm.xchange.bter.dto.marketdata.BTERTicker;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BTERMarketDataService extends BTERMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTERMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTicker ticker = super.getBTERTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return BTERAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERDepth bterDepth = super.getBTEROrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return BTERAdapters.adaptOrderBook(bterDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTERTradeHistory tradeHistory = (args != null && args.length > 0 && args[0] != null && args[0] instanceof String)
        ? super.getBTERTradeHistorySince(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), (String) args[0])
        : super.getBTERTradeHistory(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return BTERAdapters.adaptTrades(tradeHistory, currencyPair);
  }

}
