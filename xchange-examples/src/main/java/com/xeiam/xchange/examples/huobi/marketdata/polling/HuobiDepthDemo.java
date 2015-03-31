package com.xeiam.xchange.examples.huobi.marketdata.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.huobi.HuobiExchange;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiDepth;
import com.xeiam.xchange.huobi.service.polling.HuobiMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class HuobiDepthDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(HuobiExchange.class);
    Exchange huobiExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    generic(huobiExchange);
    raw(huobiExchange);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    HuobiMarketDataServiceRaw huobi = (HuobiMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest full order book data
    HuobiDepth depth = huobi.getBitVcDepth("btc");
    System.out.println("Asks: " + depth.getAsks().toString() +" Bids: " + depth.getBids().toString());
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));

  }
}
