package com.xeiam.xchange.examples.hitbtc.marketdata;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.examples.hitbtc.HitbtcExampleUtils;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class HitbtcMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange coinbaseExchange = HitbtcExampleUtils.createExchange();
    PollingMarketDataService marketDataService = coinbaseExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((HitbtcMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook);
  }

  private static void raw(HitbtcMarketDataServiceRaw marketDataService) throws IOException {

    Collection<CurrencyPair> currencyPairs = marketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    HitbtcTicker ticker = marketDataService.getHitbtcTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    HitbtcOrderBook orderBook = marketDataService.getHitbtcOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook);
  }
}
