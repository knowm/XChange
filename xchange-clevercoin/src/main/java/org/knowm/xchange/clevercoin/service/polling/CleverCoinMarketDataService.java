package org.knowm.xchange.clevercoin.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.clevercoin.CleverCoinAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Karsten Nilsen
 */
public class CleverCoinMarketDataService extends CleverCoinMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinMarketDataService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return CleverCoinAdapters.adaptTicker(getCleverCoinTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return CleverCoinAdapters.adaptOrderBook(getCleverCoinOrderBook(), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return CleverCoinAdapters.adaptTrades(getCleverCoinTransactions(args), currencyPair);
  }

}
