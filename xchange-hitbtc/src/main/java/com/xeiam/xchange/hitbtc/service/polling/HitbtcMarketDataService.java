package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades.HitbtcTradesSortOrder;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author kpysniak
 */
public class HitbtcMarketDataService extends HitbtcMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HitbtcMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return HitbtcAdapters.adaptTicker(getHitbtcTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return HitbtcAdapters.adaptOrderBook(getHitbtcOrderBook(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

      Long from = null;
      HitbtcTradesSortOrder sortBy = HitbtcTradesSortOrder.SORT_BY_TIMESTAMP;
      Long startIndex = null;
      Long max_results = null;

    if (args.length >= 4) {
        from = (Long) args[0];
        sortBy = (HitbtcTradesSortOrder) args[1];
        startIndex = (Long) args[2];
        max_results = (Long) args[3];
    }

    return HitbtcAdapters.adaptTrades(getHitbtcTrades(currencyPair, from, sortBy, startIndex, max_results), currencyPair);
  }

}
