package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.tradeogre.TradeOgreAdapters;

public class TradeOgreMarketDataService extends TradeOgreMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public TradeOgreMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return TradeOgreAdapters.adaptTicker(currencyPair, getTradeOgreTicker(currencyPair));
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return getTradeOgreTickers().collect(Collectors.toList());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return TradeOgreAdapters.adaptOrderBook(currencyPair, getTradeOgreOrderBook(currencyPair));
  }
}
