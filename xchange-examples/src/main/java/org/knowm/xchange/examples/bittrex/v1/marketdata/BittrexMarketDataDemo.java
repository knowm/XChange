package org.knowm.xchange.examples.bittrex.v1.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrency;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.service.BittrexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BittrexMarketDataDemo {

  static Exchange exchange;

  public static void main(String[] args) throws IOException {

    exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));

    generic(marketDataService);
    raw((BittrexMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    CurrencyPair pair = new CurrencyPair("ETH", "BTC");
//    CurrencyPair pair = new CurrencyPair("BTC", "USDT");
    System.out.println("Market data for " + pair + ":");
    Ticker ticker = marketDataService.getTicker(pair);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(pair);
    System.out.println(orderBook);

    Trades trades = marketDataService.getTrades(pair);
    System.out.println(trades);
  }

  private static void raw(BittrexMarketDataServiceRaw marketDataService) throws IOException {

    System.out.println("------------RAW-----------");

    BittrexCurrency[] currencies = marketDataService.getBittrexCurrencies();
    System.out.println(Arrays.toString(currencies));

    ArrayList<BittrexSymbol> symbols = marketDataService.getBittrexSymbols();
    System.out.println(symbols);

    CurrencyPair pair = exchange.getExchangeSymbols().get(new Random().nextInt(exchange.getExchangeSymbols().size()));
    System.out.println("Market data for " + pair + ":");
    String pairString = BittrexUtils.toPairString(pair);

    BittrexTicker ticker = marketDataService.getBittrexTicker(pairString);
    System.out.println(ticker);

    ArrayList<BittrexTicker> tickers = marketDataService.getBittrexTickers();
    System.out.println(tickers);

    BittrexDepth orderBook = marketDataService.getBittrexOrderBook(pairString, 50);
    System.out.println(orderBook);

    BittrexTrade[] trades = marketDataService.getBittrexTrades(pairString, 100);
    System.out.println(Arrays.asList(trades));
  }
}
