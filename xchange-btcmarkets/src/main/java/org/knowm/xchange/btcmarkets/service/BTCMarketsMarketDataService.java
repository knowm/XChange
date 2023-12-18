package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsMarketTradeParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * @author Matija Mazi (with additions from CDP)
 */
public class BTCMarketsMarketDataService extends BTCMarketsMarketDataServiceRaw
    implements MarketDataService {

  public BTCMarketsMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    BTCMarketsTicker t = getBTCMarketsTicker(currencyPair);
    return BTCMarketsAdapters.adaptTicker(currencyPair, t);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BTCMarketsAdapters.adaptOrderBook(getBTCMarketsOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BTCMarketsAdapters.adaptMarketTrades(getBTCMarketsTrade(currencyPair), currencyPair);
  }

  /**
   * @param params use {@link BTCMarketsMarketTradeParams} for params
   */
  @Override
  public Trades getTrades(Params params) throws IOException {

    return BTCMarketsAdapters.adaptMarketTrades(
        getBTCMarketsTrade(((BTCMarketsMarketTradeParams) params).currencyPair, params),
        ((BTCMarketsMarketTradeParams) params).currencyPair);
  }
}
