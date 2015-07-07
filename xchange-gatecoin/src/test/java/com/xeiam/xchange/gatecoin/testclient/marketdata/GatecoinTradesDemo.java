
package com.xeiam.xchange.gatecoin.testclient.marketdata;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.gatecoin.GatecoinExchange;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinTransaction;
import com.xeiam.xchange.gatecoin.service.polling.GatecoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import java.io.IOException;

/**
 *
 * @author sumdeha
 */
public class GatecoinTradesDemo {
    public static void main(String[] args) throws IOException {

    // Use the factory to get gatecoin exchange API using default settings
    Exchange gatecoin = ExchangeFactory.INSTANCE.createExchange(GatecoinExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = gatecoin.getPollingMarketDataService();

    generic(marketDataService);
    raw((GatecoinMarketDataServiceRaw) marketDataService);
}
    
    private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades.getTrades().toString());
    
    Trades tradesWithCount = marketDataService.getTrades(CurrencyPair.BTC_EUR,10);
    System.out.println(tradesWithCount.getTrades().toString());
    
    Trades tradesWithCountAndTxId = marketDataService.getTrades(CurrencyPair.BTC_HKD,5,(long)1386153);
    System.out.println(tradesWithCountAndTxId.getTrades().toString());
  }
     
     

  private static void raw(GatecoinMarketDataServiceRaw marketDataService) throws IOException {

    GatecoinTransaction[] gatecoinTransactions = marketDataService.getGatecoinTransactions(CurrencyPair.BTC_USD.toString()).getTransactions();

    System.out.println(gatecoinTransactions[0].toString());
  }
}
