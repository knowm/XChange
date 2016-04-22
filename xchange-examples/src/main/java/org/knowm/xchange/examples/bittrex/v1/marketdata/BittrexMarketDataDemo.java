package org.knowm.xchange.examples.bittrex.v1.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.v1.BittrexExchange;
import org.knowm.xchange.bittrex.v1.BittrexUtils;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexCurrency;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.v1.service.polling.BittrexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class BittrexMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    PollingMarketDataService pollingMarketDataService = exchange.getPollingMarketDataService();

    generic(pollingMarketDataService);
    raw((BittrexMarketDataServiceRaw) pollingMarketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    CurrencyPair pair = CurrencyPair.BTC_USD;
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

    ArrayList<CurrencyPair> pairs = new ArrayList<CurrencyPair>(marketDataService.getExchangeSymbols());
    System.out.println(pairs);

    CurrencyPair pair = pairs.get(new Random().nextInt(pairs.size()));
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
