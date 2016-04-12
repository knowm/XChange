package org.knowm.xchange.examples.anx.v2.marketdata;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXDepth;
import org.knowm.xchange.anx.v2.dto.marketdata.ANXTicker;
import org.knowm.xchange.anx.v2.service.polling.ANXMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class ANXMarketdataDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the public market data feed (no authentication)
    PollingMarketDataService marketDataService = anx.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    String btcusd = ticker.getLast().toString();
    System.out.println("Current exchange rate for BTC / USD: " + btcusd);

    // Get the current orderbook
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println("Current Order Book size for BTC / USD: " + orderBook.getAsks().size() + orderBook.getBids().size());

    // Get the current full orderbook
    // OrderBook fullOrderBook = marketDataService.getFullOrderBook(Currency.BTC, Currency.USD);
    // System.out.println("Current Full Order Book size for BTC / USD: " + fullOrderBook.getAsks().size() + fullOrderBook.getBids().size());

    // Get trades
    // Trades trades = marketDataService.getTrades(Currency.BTC, Currency.PLN);
    // System.out.println("Current trades size for BTC / PLN: " + trades.getTrades().size());

    ANXMarketDataServiceRaw marketDataServiceRaw = (ANXMarketDataServiceRaw) marketDataService;

    // Get all tickers
    Map<String, ANXTicker> tickers = marketDataServiceRaw.getANXTickers(anx.getMetaData().getMarketMetaDataMap().keySet());
    System.out.println(tickers);

    // Get all orderbooks
    Map<String, ANXDepth> orderbooks = marketDataServiceRaw.getANXFullOrderBooks(anx.getMetaData().getMarketMetaDataMap().keySet());
    System.out.println(orderbooks);

  }

}
