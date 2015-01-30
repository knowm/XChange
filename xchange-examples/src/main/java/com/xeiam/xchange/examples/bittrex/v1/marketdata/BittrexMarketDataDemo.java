package com.xeiam.xchange.examples.bittrex.v1.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bittrex.v1.BittrexExchange;
import com.xeiam.xchange.bittrex.v1.BittrexUtils;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexCurrency;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexDepth;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexSymbol;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTicker;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTrade;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BittrexMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    PollingMarketDataService pollingMarketDataService = exchange.getPollingMarketDataService();

    generic(pollingMarketDataService);
    raw((BittrexMarketDataServiceRaw) pollingMarketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    ArrayList<CurrencyPair> pairs = new ArrayList<CurrencyPair>(marketDataService.getExchangeSymbols());
    System.out.println(pairs);

    CurrencyPair pair = pairs.get(new Random().nextInt(pairs.size()));
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
