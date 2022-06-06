package org.knowm.xchange.examples.coincheck.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coincheck.CoincheckExchange;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTicker;
import org.knowm.xchange.coincheck.service.CoincheckMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrates requesting the ticker from Coincheck. */
public class CoincheckTickerDemo {
  private static final CurrencyPair pair = CurrencyPair.BTC_JPY;

  public static void main(String[] args) throws IOException {
    // Use the factory to get Coincheck exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoincheckExchange.class);

    // Create market data service.
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((CoincheckMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_JPY);
    System.out.println(ticker.toString());
  }

  private static void raw(CoincheckMarketDataServiceRaw marketDataService) throws IOException {
    CoincheckTicker ticker =
        marketDataService.getCoincheckTicker(new CoincheckPair(CurrencyPair.BTC_JPY));
    System.out.println(ticker.toString());
  }
}
