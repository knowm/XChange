package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class KrakenMarketDataService extends KrakenMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return KrakenAdapters.adaptTicker(getKrakenTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    long count = Long.MAX_VALUE;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        count = (Long) arg0;
      } else if (arg0 instanceof Integer) {
        count = (Integer) arg0;
      } else {
        throw new ExchangeException("args[0] must be of type Long or Integer");
      }
    }

    KrakenDepth krakenDepth = getKrakenDepth(currencyPair, count);

    return KrakenAdapters.adaptOrderBook(krakenDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long since = null;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        since = (Long) arg0;
      } else {
        throw new ExchangeException("args[0] must be of type Long!");
      }
    }

    KrakenPublicTrades krakenTrades = getKrakenTrades(currencyPair, since);
    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getTrades(), currencyPair, krakenTrades.getLast());
    return trades;
  }

}
