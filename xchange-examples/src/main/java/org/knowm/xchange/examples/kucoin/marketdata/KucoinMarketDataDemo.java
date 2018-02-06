package org.knowm.xchange.examples.kucoin.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinCoin;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinDealOrder;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinOrderBook;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;
import org.knowm.xchange.kucoin.service.KucoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KucoinMarketDataDemo {

  private static final CurrencyPair PAIR = CurrencyPair.ETH_BTC;
  static Exchange exchange;

  public static void main(String[] args) throws IOException {

    exchange = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));

    generic(marketDataService);
    raw((KucoinMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    System.out.println("Market data for " + PAIR + ":");
    Ticker ticker = marketDataService.getTicker(PAIR);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(PAIR);
    System.out.println(orderBook);

    Trades trades = marketDataService.getTrades(PAIR);
    System.out.println(trades);
  }

  private static void raw(KucoinMarketDataServiceRaw marketDataService) throws IOException {

    System.out.println("------------RAW-----------");

    KucoinResponse<KucoinTicker> tickerResponse = marketDataService.getKucoinTicker(PAIR);
    System.out.println(tickerResponse.getData());

    KucoinResponse<List<KucoinTicker>> tickersResponse = marketDataService.getKucoinTickers();
    System.out.println(tickersResponse.getData());

    KucoinResponse<KucoinOrderBook> orderBookResponse = marketDataService.getKucoinOrderBook(PAIR, 10);
    System.out.println(orderBookResponse.getData());

    KucoinResponse<List<KucoinDealOrder>> tradesResponse = marketDataService.getKucoinTrades(PAIR, 10, null);
    System.out.println(Arrays.asList(tradesResponse.getData()));

    KucoinResponse<List<KucoinCoin>> currenciesResponse = marketDataService.getKucoinCurrencies();
    System.out.println(currenciesResponse.getData());
  }
}
