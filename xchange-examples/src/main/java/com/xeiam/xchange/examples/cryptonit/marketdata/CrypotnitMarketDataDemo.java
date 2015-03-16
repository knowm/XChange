package com.xeiam.xchange.examples.cryptonit.marketdata;

import java.io.IOException;
import java.util.Collection;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.cryptonit.v2.service.polling.CryptonitMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.cryptonit.CryptonitExampleUtils;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class CrypotnitMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange cryptonitExchange = CryptonitExampleUtils.createExchange();
    PollingMarketDataService marketDataService = cryptonitExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((CryptonitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Collection<CurrencyPair> currencyPairs = marketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook);

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades);
  }

  private static void raw(CryptonitMarketDataServiceRaw marketDataService) throws IOException {

    CryptonitTicker ticker = marketDataService.getCryptonitTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    CryptonitOrders marketDepth = marketDataService.getCryptonitAsks(CurrencyPair.BTC_USD, 10);
    System.out.println(marketDepth);

    CryptonitOrders trades = marketDataService.getCryptonitTrades(CurrencyPair.BTC_USD, 10);
    System.out.println(trades);
  }
}
