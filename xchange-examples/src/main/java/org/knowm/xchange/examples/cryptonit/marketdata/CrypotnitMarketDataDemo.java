package org.knowm.xchange.examples.cryptonit.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import org.knowm.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit.v2.service.CryptonitMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.cryptonit.CryptonitExampleUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CrypotnitMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange cryptonitExchange = CryptonitExampleUtils.createExchange();
    MarketDataService marketDataService = cryptonitExchange.getMarketDataService();

    generic(marketDataService);
    raw((CryptonitMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

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
