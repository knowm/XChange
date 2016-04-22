package org.knowm.xchange.bitstamp.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;
import org.knowm.xchange.utils.Assert;

/**
 * @author Matija Mazi
 */
public class BitstampMarketDataService extends BitstampMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitstampMarketDataService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    Assert.isTrue(currencyPair.equals(CurrencyPair.BTC_USD), "Currency Pair must be USD/BTC!!!");
    return BitstampAdapters.adaptTicker(getBitstampTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    Assert.isTrue(currencyPair.equals(CurrencyPair.BTC_USD), "Currency Pair must be USD/BTC!!!");
    return BitstampAdapters.adaptOrderBook(getBitstampOrderBook(), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Assert.isTrue(currencyPair.equals(CurrencyPair.BTC_USD), "Currency Pair must be USD/BTC!!!");
    return BitstampAdapters.adaptTrades(getBitstampTransactions(args), currencyPair);
  }

}
