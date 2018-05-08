package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Author: bryant_harris */
public class AbucoinsMarketDataService extends AbucoinsMarketDataServiceRaw
    implements MarketDataService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return AbucoinsAdapters.adaptTicker(
        getAbucoinsTicker(AbucoinsAdapters.adaptCurrencyPairToProductID(currencyPair)),
        currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    AbucoinsOrderBook orderBook =
        getAbucoinsOrderBook(AbucoinsAdapters.adaptCurrencyPairToProductID(currencyPair));

    return AbucoinsAdapters.adaptOrderBook(orderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    AbucoinsTrade[] trades =
        getAbucoinsTrades(AbucoinsAdapters.adaptCurrencyPairToProductID(currencyPair));

    return AbucoinsAdapters.adaptTrades(trades, currencyPair);
  }
}
