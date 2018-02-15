package org.knowm.xchange.examples.zaif.market;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.zaif.ZaifExchange;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;
import org.knowm.xchange.zaif.service.ZaifMarketDataServiceRaw;

public class ZaifMarketDataDemo {
  private static final CurrencyPair PAIR = CurrencyPair.BTC_JPY;
  static Exchange exchange;

  public static void main(String[] args) throws IOException {

    exchange = ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));

    generic(marketDataService);
    raw((ZaifMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    System.out.println("----------GENERIC---------");

    OrderBook orderBook = marketDataService.getOrderBook(PAIR);
    System.out.println(orderBook);
  }

  private static void raw(ZaifMarketDataServiceRaw marketDataService) throws IOException {

    System.out.println("------------RAW-----------");

    List<ZaifMarket> markets = marketDataService.getAllMarkets();
    System.out.println(markets);

    ZaifFullBook orderBookResponse = marketDataService.getZaifFullBook(PAIR);
    System.out.println(orderBookResponse);
  }
}
