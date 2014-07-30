package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author Matija Mazi
 */
public class BitstampMarketDataService extends BitstampMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitstampMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitstampAdapters.adaptTicker(getBitstampTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitstampAdapters.adaptOrders(getBitstampOrderBook(), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitstampAdapters.adaptTrades(getBitstampTransactions(args), currencyPair);
  }

}
