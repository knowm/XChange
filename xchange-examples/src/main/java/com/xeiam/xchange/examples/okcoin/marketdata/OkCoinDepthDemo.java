package com.xeiam.xchange.examples.okcoin.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinDepth;
import com.xeiam.xchange.okcoin.service.polling.OkCoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class OkCoinDepthDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);

    // flag to set Use_Intl (USD) or China (default)
    exSpec.setExchangeSpecificParametersItem("Use_Intl", false);
    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    generic(okcoinExchange);
    raw(okcoinExchange);
  }

  private static void generic(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = okcoinExchange.getPollingMarketDataService();

    // Get the latest full order book data for NMC/XRP
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

  }

  private static void raw(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    OkCoinMarketDataServiceRaw okCoinMarketDataServiceRaw = (OkCoinMarketDataServiceRaw) okcoinExchange.getPollingMarketDataService();

    // Get the latest full order book data
    OkCoinDepth depth = okCoinMarketDataServiceRaw.getDepth(CurrencyPair.BTC_CNY);
    System.out.println(depth.toString());
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));

  }
}
