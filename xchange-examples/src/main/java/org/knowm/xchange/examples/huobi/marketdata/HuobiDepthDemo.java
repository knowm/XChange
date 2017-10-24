package org.knowm.xchange.examples.huobi.marketdata;

import java.io.IOException;
import java.util.Arrays;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.huobi.HuobiExchange;
import org.knowm.xchange.huobi.dto.marketdata.HuobiDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcExchangeRate;
import org.knowm.xchange.huobi.service.BitVcFuturesMarketDataServiceRaw;
import org.knowm.xchange.huobi.service.HuobiMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

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

    MarketDataService marketDataService = exchange.getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_CNY);

    System.out.println(ticker);

    BitVcFuturesMarketDataServiceRaw raw = (BitVcFuturesMarketDataServiceRaw) marketDataService;
    BitVcExchangeRate bitVcExchangeRate = raw.getBitVcExchangeRate();

    System.out.println("Current exchange rate: " + bitVcExchangeRate.getRate());
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(orderBook.toString());
    System.out.println("full orderbook size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    HuobiMarketDataServiceRaw huobi = (HuobiMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the latest full order book data
    HuobiDepth depth = huobi.getBitVcDepth("btc");
    System.out.println("Asks: " + Arrays.deepToString(depth.getAsks()) + " Bids: " + Arrays.deepToString(depth.getBids()));
    System.out.println("size: " + (depth.getAsks().length + depth.getBids().length));

  }
}
