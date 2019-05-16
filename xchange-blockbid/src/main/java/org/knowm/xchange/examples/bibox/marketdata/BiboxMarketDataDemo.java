package org.knowm.xchange.examples.bibox.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bibox.BiboxExchange;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.bibox.service.BiboxMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BiboxMarketDataDemo {

  private static final CurrencyPair PAIR = CurrencyPair.ETH_BTC;
  static Exchange exchange;

  public static void main(String[] args) throws IOException {

    exchange = ExchangeFactory.INSTANCE.createExchange(BiboxExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));

    generic(marketDataService);
    raw((BiboxMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    System.out.println("Market data for " + PAIR + ":");
    Ticker ticker = marketDataService.getTicker(PAIR);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(PAIR);
    System.out.println(orderBook);
  }

  private static void raw(BiboxMarketDataServiceRaw marketDataService) throws IOException {

    System.out.println("------------RAW-----------");

    BiboxTicker tickerResponse = marketDataService.getBiboxTicker(PAIR);
    System.out.println(tickerResponse);

    List<BiboxMarket> tickersResponse = marketDataService.getAllBiboxMarkets();
    System.out.println(tickersResponse);

    BiboxOrderBook orderBookResponse = marketDataService.getBiboxOrderBook(PAIR, 10);
    System.out.println(orderBookResponse);
  }
}
