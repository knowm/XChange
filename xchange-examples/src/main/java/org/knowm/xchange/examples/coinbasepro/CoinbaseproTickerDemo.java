package org.knowm.xchange.examples.coinbasepro;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinbaseproTickerDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((CoinbaseProMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(CoinbaseProMarketDataServiceRaw marketDataService) throws IOException {

    CoinbaseProProductTicker coinbaseProTicker =
        marketDataService.getCoinbaseProProductTicker(CurrencyPair.BTC_USD);

    System.out.println(coinbaseProTicker.toString());
  }
}
