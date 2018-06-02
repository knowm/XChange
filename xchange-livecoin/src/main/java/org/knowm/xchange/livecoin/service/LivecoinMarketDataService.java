package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class LivecoinMarketDataService extends LivecoinMarketDataServiceRaw
    implements MarketDataService {

  public LivecoinMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    LivecoinTicker ticker = getLivecoinTicker(currencyPair);
    return LivecoinAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    final List<CurrencyPair> currencyPairs = new ArrayList<>();
    if (params instanceof CurrencyPairsParam) {
      currencyPairs.addAll(((CurrencyPairsParam) params).getCurrencyPairs());
    }
    return getAllTickers()
        .stream()
        .map(
            livecoinTicker ->
                LivecoinAdapters.adaptTicker(
                    livecoinTicker,
                    CurrencyPairDeserializer.getCurrencyPairFromString(livecoinTicker.getSymbol())))
        .filter(
            ticker -> currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    int depth = 50;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Number) {
        Number arg = (Number) args[0];
        depth = arg.intValue();
      }
    }

    return LivecoinAdapters.adaptOrderBook(
        getOrderBookRaw(currencyPair, depth, Boolean.TRUE), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return LivecoinAdapters.adaptTrades(getTrades(currencyPair), currencyPair);
  }
}
