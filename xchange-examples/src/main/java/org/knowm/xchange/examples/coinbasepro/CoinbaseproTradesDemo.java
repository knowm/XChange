package org.knowm.xchange.examples.coinbasepro;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinbaseproTradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((CoinbaseProMarketDataServiceRaw) marketDataService);
  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades);
  }

  public static void raw(CoinbaseProMarketDataServiceRaw marketDataServiceRaw) throws IOException {

    CoinbaseProTrade[] trades = marketDataServiceRaw.getCoinbaseProTrades(CurrencyPair.BTC_USD);
    System.out.println(Arrays.toString(trades));
  }
}
