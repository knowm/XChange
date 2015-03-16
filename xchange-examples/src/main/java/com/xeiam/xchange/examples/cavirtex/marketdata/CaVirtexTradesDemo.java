package com.xeiam.xchange.examples.cavirtex.marketdata;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.virtex.v2.VirtExExchange;
import com.xeiam.xchange.virtex.v2.dto.marketdata.VirtExTrade;
import com.xeiam.xchange.virtex.v2.service.polling.VirtExMarketDataServiceRaw;

/**
 * Demonstrate requesting Trades at VirtEx
 */
public class CaVirtexTradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get the VirtEx exchange API using default settings
    Exchange cavirtex = ExchangeFactory.INSTANCE.createExchange(VirtExExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = cavirtex.getPollingMarketDataService();

    generic(marketDataService);
    raw((VirtExMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/CAD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CAD);
    System.out.println("Trades, Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(VirtExMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/CAD
    List<VirtExTrade> trades = marketDataService.getVirtExTrades(CurrencyPair.BTC_CAD);
    System.out.println("Trades, default. Size= " + trades.size());
    System.out.println(trades.toString());
  }

}
