package org.knowm.xchange.latoken.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.latoken.LatokenAdapters;
import org.knowm.xchange.latoken.LatokenErrorAdapter;
import org.knowm.xchange.latoken.dto.LatokenException;
import org.knowm.xchange.latoken.dto.exchangeinfo.LatokenTime;
import org.knowm.xchange.latoken.dto.marketdata.LatokenOrderbook;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTicker;
import org.knowm.xchange.latoken.dto.marketdata.LatokenTrades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class LatokenMarketDataService extends LatokenMarketDataServiceRaw
    implements MarketDataService {

  public static final int maxOrderbookDepth = 100;
  public static final int maxTrades = 100;

  public LatokenMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {

    try {
      LatokenTicker latokenTicker = getLatokenTicker(pair);
      return LatokenAdapters.adaptTicker(latokenTicker);
    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {

    try {
      List<LatokenTicker> latokenTickers = getLatokenTickers();

      return latokenTickers.stream()
          .map(latokenTicker -> LatokenAdapters.adaptTicker(latokenTicker))
          .collect(Collectors.toList());

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {

    try {
      int depthLimit = maxOrderbookDepth;

      if (args != null && args.length == 1) {
        Object arg0 = args[0];
        if (!(arg0 instanceof Integer)) {
          throw new ExchangeException("Depth-limit must be an Integer!");
        } else {
          depthLimit = (Integer) arg0;
        }
      }
      LatokenOrderbook latokenOrderbook = getLatokenOrderbook(pair, depthLimit);
      return LatokenAdapters.adaptOrderBook(this.exchange, latokenOrderbook);

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {

    try {
      int limit = maxTrades;

      if (args != null && args.length == 1) {
        Object arg0 = args[0];
        if (!(arg0 instanceof Integer)) {
          throw new ExchangeException("Maximal number of trades must be an Integer!");
        } else {
          limit = (Integer) arg0;
        }
      }
      LatokenTrades latokenTrades = getLatokenTrades(pair, limit);
      return LatokenAdapters.adaptTrades(this.exchange, latokenTrades);

    } catch (LatokenException e) {
      throw LatokenErrorAdapter.adapt(e);
    }
  }

  // ---------------------- Exchange Info -----------------------------------

  public Date getTime() throws IOException {

    LatokenTime latokenTime = latoken.getTime();
    return latokenTime.getTime();
  }
}
