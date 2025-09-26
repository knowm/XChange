package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * @author Matija Mazi
 */
public class BitstampMarketDataService extends BitstampMarketDataServiceRaw
    implements MarketDataService {

  public BitstampMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitstampAdapters.adaptTicker(getBitstampTicker(currencyPair), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    List<BitstampTicker> bitstampTickers = getBitstampTickers();
    return bitstampTickers.stream()
        .map(
            bitstampTicker ->
                BitstampAdapters.adaptTicker(
                    bitstampTicker, new CurrencyPair(bitstampTicker.getPair())))
        .collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitstampAdapters.adaptOrderBook(getBitstampOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    BitstampTime time = args.length > 0 ? (BitstampTime) args[0] : null;
    return BitstampAdapters.adaptTrades(getTransactions(currencyPair, time), currencyPair);
  }
}
