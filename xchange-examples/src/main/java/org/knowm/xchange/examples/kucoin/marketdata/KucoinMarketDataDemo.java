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
import org.knowm.xchange.kucoin.KucoinMarketDataService;
import org.knowm.xchange.kucoin.dto.response.OrderBookResponse;
import org.knowm.xchange.kucoin.dto.response.SymbolResponse;
import org.knowm.xchange.kucoin.dto.response.SymbolTickResponse;
import org.knowm.xchange.kucoin.dto.response.TickerResponse;
import org.knowm.xchange.kucoin.dto.response.TradeHistoryResponse;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KucoinMarketDataDemo {

  private static final CurrencyPair PAIR = CurrencyPair.ETH_BTC;
  static Exchange exchange;

  public static void main(String[] args) throws IOException {

    exchange = ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));

    generic(marketDataService);
    raw((KucoinMarketDataService) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    System.out.println("Market data for " + PAIR + ":");
    Ticker ticker = marketDataService.getTicker(PAIR);
    System.out.println(ticker);

    OrderBook orderBookPartial = marketDataService.getOrderBook(PAIR);
    System.out.println(orderBookPartial);

    OrderBook orderBookFull =
        marketDataService.getOrderBook(PAIR, KucoinMarketDataService.PARAM_FULL_ORDERBOOK);
    System.out.println(orderBookFull);

    Trades trades = marketDataService.getTrades(PAIR);
    System.out.println(trades);
  }

  private static void raw(KucoinMarketDataService marketDataService) throws IOException {

    System.out.println("------------RAW-----------");

    TickerResponse tickerResponse = marketDataService.getKucoinTicker(PAIR);
    System.out.println(tickerResponse);

    SymbolTickResponse statsResponse = marketDataService.getKucoin24hrStats(PAIR);
    System.out.println(statsResponse);

    OrderBookResponse orderBookResponsePartial = marketDataService.getKucoinOrderBookPartial(PAIR);
    System.out.println(orderBookResponsePartial);

    OrderBookResponse orderBookResponseFull = marketDataService.getKucoinOrderBookFull(PAIR);
    System.out.println(orderBookResponseFull);

    List<TradeHistoryResponse> tradesResponse = marketDataService.getKucoinTrades(PAIR);
    System.out.println(tradesResponse);

    List<SymbolResponse> currenciesResponse = marketDataService.getKucoinSymbols();
    System.out.println(currenciesResponse);
  }
}
