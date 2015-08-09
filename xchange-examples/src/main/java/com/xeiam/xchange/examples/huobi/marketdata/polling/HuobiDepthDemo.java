package com.xeiam.xchange.examples.huobi.marketdata.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.huobi.HuobiExchange;
import com.xeiam.xchange.huobi.dto.marketdata.HuobiDepth;
import com.xeiam.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import com.xeiam.xchange.huobi.service.polling.BitVcFuturesMarketDataServiceRaw;
import com.xeiam.xchange.huobi.service.polling.HuobiMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class HuobiDepthDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(HuobiExchange.class);
    exSpec.setExchangeSpecificParametersItem("use_bitvc", false);

    Exchange huobiExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    futures(exSpec);
    generic(huobiExchange);
    raw(huobiExchange);
  }

  private static void futures(ExchangeSpecification exSpec) throws IOException {
    exSpec.setExchangeSpecificParametersItem("use_bitvc", true);
    exSpec.setExchangeSpecificParametersItem("use_bitvc_futures", true);
    exSpec.setExchangeSpecificParametersItem("Futures_Contract_String", "ThisWeek");

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    PollingMarketDataService pollingMarketDataService = exchange.getPollingMarketDataService();

    Ticker ticker = pollingMarketDataService.getTicker(CurrencyPair.BTC_CNY);

    System.out.println(ticker);

    BitVcFuturesMarketDataServiceRaw raw = (BitVcFuturesMarketDataServiceRaw) pollingMarketDataService;
    BitVcExchangeRate bitVcExchangeRate = raw.getBitVcExchangeRate();

    System.out.println("Current exchange rate: " + bitVcExchangeRate.getRate());
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
    System.out.println("Asks: " + depth.getAsks().toString() + " Bids: " + depth.getBids().toString());
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));

  }
}
