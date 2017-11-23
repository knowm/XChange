package org.knowm.xchange.examples.itbit.market;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.itbit.v1.ItBitExchange;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitDepth;
import org.knowm.xchange.itbit.v1.service.ItBitMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Created by joseph on 6/15/17.
 */
public class ItBitOrderBookDemo {
  public static void main(String[] args) throws IOException {
    Exchange xchange = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());

    MarketDataService marketDataService = xchange.getMarketDataService();

    generic(marketDataService);
    raw((ItBitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());

    OrderBook orderBookAsXBT = marketDataService.getOrderBook(new CurrencyPair(Currency.XBT, Currency.USD));
    System.out.println(orderBookAsXBT.toString());
  }

  private static void raw(ItBitMarketDataServiceRaw marketDataService) throws IOException {

    ItBitDepth orderBook = marketDataService.getItBitDepth(CurrencyPair.BTC_USD);
    System.out.println(orderBook.toString());
  }
}
