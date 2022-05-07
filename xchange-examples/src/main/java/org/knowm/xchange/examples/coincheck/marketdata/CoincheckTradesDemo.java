package org.knowm.xchange.examples.coincheck.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coincheck.CoincheckExchange;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPagination;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckPair;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTradesContainer;
import org.knowm.xchange.coincheck.service.CoincheckMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrates requesting public trades from Coincheck. */
public class CoincheckTradesDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Coincheck exchange API using default settings
    Exchange coincheck = ExchangeFactory.INSTANCE.createExchange(CoincheckExchange.class);

    // Get the market data service.
    MarketDataService marketDataService = coincheck.getMarketDataService();

    generic(marketDataService);
    raw((CoincheckMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    // Get the latest trade data.
    Trades defaultTrades = marketDataService.getTrades(CurrencyPair.BTC_JPY);
    System.out.println(
        String.format(
            "Coincheck returned %s trades by default.", defaultTrades.getTrades().size()));

    // Get a specific number of trades.
    CoincheckPagination pagination = CoincheckPagination.builder().limit(100).build();
    Trades limitedTrades = marketDataService.getTrades(CurrencyPair.BTC_JPY, pagination);
    System.out.println(
        String.format(
            "When requesting 100 trades, Coincheck returned %s.",
            limitedTrades.getTrades().size()));

    System.out.println(limitedTrades.getTrades().get(0).toString());
  }

  private static void raw(CoincheckMarketDataServiceRaw marketDataService) throws IOException {
    // Get the latest trade data.
    CoincheckTradesContainer defaultTrades =
        marketDataService.getCoincheckTrades(new CoincheckPair(CurrencyPair.BTC_JPY), null);
    System.out.println(
        String.format(
            "Coincheck raw returned %s trades by default.", defaultTrades.getData().size()));

    // Get a specific number of trades.
    CoincheckPagination pagination = CoincheckPagination.builder().limit(100).build();
    CoincheckTradesContainer limitedTrades =
        marketDataService.getCoincheckTrades(new CoincheckPair(CurrencyPair.BTC_JPY), pagination);
    System.out.println(
        String.format(
            "When requesting 100 trades, Coincheck raw returned %s.",
            limitedTrades.getData().size()));

    System.out.println(limitedTrades.getData().get(0).toString());
  }
}
