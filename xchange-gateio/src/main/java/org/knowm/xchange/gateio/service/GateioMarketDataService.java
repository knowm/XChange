package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class GateioMarketDataService extends GateioMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTicker ticker = super.getBTERTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTicker(currencyPair, ticker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioDepth gateioDepth = super.getBTEROrderBook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptOrderBook(gateioDepth, currencyPair);
  }

  public Map<CurrencyPair, OrderBook> getOrderBooks() throws IOException {

    Map<CurrencyPair, GateioDepth> gateioDepths = super.getGateioDepths();
    Map<CurrencyPair, OrderBook> orderBooks = new HashMap<>(gateioDepths.size());

    gateioDepths.forEach((currencyPair, gateioDepth) -> {
      OrderBook orderBook = GateioAdapters.adaptOrderBook(gateioDepth, currencyPair);
      orderBooks.put(currencyPair, orderBook);
    });

    return orderBooks;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    GateioTradeHistory tradeHistory = (args != null && args.length > 0 && args[0] != null && args[0] instanceof String)
        ? super.getBTERTradeHistorySince(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), (String) args[0])
        : super.getBTERTradeHistory(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return GateioAdapters.adaptTrades(tradeHistory, currencyPair);
  }

}
