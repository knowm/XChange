package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.tradeogre.TradeOgreAdapters;
import org.knowm.xchange.tradeogre.dto.market.TradeOgreOrderBook;
import org.knowm.xchange.tradeogre.dto.market.TradeOgreTicker;

public class TradeOgreMarketDataServiceRaw extends TradeOgreBaseService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public TradeOgreMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public TradeOgreTicker getTradeOgreTicker(CurrencyPair market) throws IOException {
    return tradeOgre.getTicker(TradeOgreAdapters.adaptCurrencyPair(market));
  }

  public Stream<Ticker> getTradeOgreTickers() throws IOException {
    return tradeOgre.getTickers().stream()
        .map(Map::entrySet)
        .flatMap(Collection::stream)
        .map(
            entry ->
                TradeOgreAdapters.adaptTicker(
                    TradeOgreAdapters.adaptTradeOgreCurrencyPair(entry.getKey()), entry.getValue()));
  }

  public TradeOgreOrderBook getTradeOgreOrderBook(CurrencyPair currencyPair) throws IOException {
    String market = TradeOgreAdapters.adaptCurrencyPair(currencyPair);
    return tradeOgre.getOrderBook(market);
  }
}
