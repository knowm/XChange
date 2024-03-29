package org.knowm.xchange.examples.bittrex.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.service.BittrexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BittrexMarketDataDemo {

  static Exchange exchange;

  public static void main(String[] args) throws IOException {

    exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    System.out.println(Arrays.toString(exchange.getExchangeInstruments().toArray()));

    generic(marketDataService);
    raw((BittrexMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    CurrencyPair pair = new CurrencyPair("ETH", "BTC");
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

    // List<BittrexMarketSummary> currencies = marketDataService.getBittrexMarketSummaries();
    // System.out.println(currencies);

    List<BittrexSymbol> symbols = marketDataService.getBittrexSymbols();
    System.out.println(symbols);

    Instrument pair =
        exchange
            .getExchangeInstruments()
            .get(new Random().nextInt(exchange.getExchangeInstruments().size()));
    System.out.println("Market data for " + pair + ":");
    String pairString = BittrexUtils.toPairString(pair);

    BittrexMarketSummary merketSummary = marketDataService.getBittrexMarketSummary(pairString);
    System.out.println(merketSummary);

    List<BittrexMarketSummary> marketSummaries = marketDataService.getBittrexMarketSummaries();
    System.out.println(marketSummaries);

    BittrexMarketDataServiceRaw.SequencedOrderBook orderBook =
        marketDataService.getBittrexSequencedOrderBook(pairString, 50);
    System.out.println(orderBook);

    List<BittrexTrade> trades = marketDataService.getBittrexTrades(pairString);
    System.out.println(trades);

    BittrexTicker ticker = marketDataService.getBittrexTicker(pairString);
    System.out.println(ticker);

    /* TODO missing api
    List<BittrexChartData> chartData =
        marketDataService..getBittrexChartData(
            CurrencyPair.ETH_BTC, BittrexChartDataPeriodType.ONE_DAY);
    System.out.println(chartData);

    List<BittrexChartData> latestTick =
        marketDataService.getBittrexLatestTick(
            CurrencyPair.ETH_BTC, BittrexChartDataPeriodType.ONE_DAY, 1500915289434L);
    System.out.println(latestTick);
    */
  }
}
