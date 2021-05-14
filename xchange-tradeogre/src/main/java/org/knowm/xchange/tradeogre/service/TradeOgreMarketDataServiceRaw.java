package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.tradeogre.TradeOgreAdapters;
import org.knowm.xchange.tradeogre.dto.marketdata.TradeOgreTicker;

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

  public List<Ticker> getTradeOgreTickers() throws IOException {
    return tradeOgre.getTickers().stream()
        .map(Map::entrySet)
        .flatMap(Collection::stream)
        .map(
            entry ->
                TradeOgreAdapters.adaptTicker(
                    TradeOgreAdapters.adaptTradeOgreCurrencyPair(entry.getKey()), entry.getValue()))
        .collect(Collectors.toList());
  }
}
