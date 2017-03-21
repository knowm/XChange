package org.knowm.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.service.BitcoindeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoindeTradesDemo {

  public static void main(String[] args) throws IOException {

    /* get the api key from args */
    if (args.length != 1) {
      System.err.println("Usage: java BitcoindeTradesDemo <api_key>");
      System.exit(1);
    }
    final String API_KEY = args[0];

    /* configure the exchange to use our api key */
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
    exchangeSpecification.setApiKey(API_KEY);

    /* create the exchange object */
    Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    /* create a data service from the exchange */
    MarketDataService marketDataService = bitcoindeExchange.getMarketDataService();

    generic(marketDataService);
    raw((BitcoindeMarketDataServiceRaw) marketDataService);
  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    /* get Trades data */
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    List<Trade> allTrades = trades.getTrades();
    System.out.println("Number trades received: " + allTrades.size());
    for (Trade t : allTrades) {
      System.out.println(t);
    }

  }

  public static void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeTrades data */
    BitcoindeTrade[] bitcoindeTrades = marketDataService.getBitcoindeTrades();

    /* print each trade object */
    for (BitcoindeTrade bitcoindeTrade : bitcoindeTrades)
      System.out.println(bitcoindeTrade);
  }
}
