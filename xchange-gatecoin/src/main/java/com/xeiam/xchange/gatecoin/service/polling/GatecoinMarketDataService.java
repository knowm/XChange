package com.xeiam.xchange.gatecoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.gatecoin.GatecoinAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Sumedha
 */
public class GatecoinMarketDataService extends GatecoinMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinMarketDataService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return GatecoinAdapters.adaptTicker(getGatecoinTicker().getTicker(), currencyPair);
  }
   

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return GatecoinAdapters.adaptOrderBook(getGatecoinOrderBook(currencyPair.toString()), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
      if(args.length == 0)
      {
          return GatecoinAdapters.adaptTrades( getGatecoinTransactions(currencyPair.toString()).getTransactions(), currencyPair);
      }
      else if(args.length == 1)
      {
          return GatecoinAdapters.adaptTrades( getGatecoinTransactions(currencyPair.toString(),(Integer)args[0],0).getTransactions(), currencyPair);
      }
      else if(args.length == 2)
      {
          return GatecoinAdapters.adaptTrades( getGatecoinTransactions(currencyPair.toString(),(Integer)args[0],(Long)args[1]).getTransactions(), currencyPair);
      }
      return null;
  
  }

}
