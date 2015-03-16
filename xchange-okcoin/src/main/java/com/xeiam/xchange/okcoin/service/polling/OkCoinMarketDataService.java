package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTrade;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class OkCoinMarketDataService extends OkCoinMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkCoinAdapters.adaptTicker(getTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return OkCoinAdapters.adaptOrderBook(getDepth(currencyPair), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    final OkCoinTrade[] trades;

    if (args.length == 0) {
      trades = getTrades(currencyPair);
    } else {
      trades = getTrades(currencyPair, (Long) args[0]);
    }
    return OkCoinAdapters.adaptTrades(trades, currencyPair);
  }
}
