package com.xeiam.xchange.examples.cryptotrade.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cryptotrade.CryptoTradeExchange;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradePair;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeDepth;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradePublicTrade;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import com.xeiam.xchange.utils.CertHelper;

public class CryptoTradeMarketDataDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange coinbaseExchange = ExchangeFactory.INSTANCE.createExchange(CryptoTradeExchange.class.getName());
    PollingMarketDataService marketDataService = coinbaseExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((CryptoTradeMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Collection<CurrencyPair> currencyPairs = marketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);
    System.out.println(orderBook);

    Trades publicTradeHistory = marketDataService.getTrades(CurrencyPair.BTC_USD, 1405805427);
    System.out.println(publicTradeHistory);
  }

  private static void raw(CryptoTradeMarketDataServiceRaw marketDataService) throws IOException {

    Map<CurrencyPair, CryptoTradePair> currencyPairs = marketDataService.getCryptoTradePairs();
    System.out.println(currencyPairs);

    CryptoTradePair currencyPair = marketDataService.getCryptoTradePairInfo(CurrencyPair.BTC_USD);
    System.out.println(currencyPair);

    CryptoTradeTicker ticker = marketDataService.getCryptoTradeTicker(CurrencyPair.BTC_USD);
    System.out.println(ticker);

    Map<CurrencyPair, CryptoTradeTicker> tickers = marketDataService.getCryptoTradeTickers();
    System.out.println(tickers);

    CryptoTradeDepth marketDepth = marketDataService.getCryptoTradeOrderBook(CurrencyPair.BTC_USD);
    System.out.println(marketDepth);

    List<CryptoTradePublicTrade> publicTradeHistory = marketDataService.getCryptoTradeTradeHistory(CurrencyPair.BTC_USD, 1405805427);
    System.out.println(publicTradeHistory);
  }
}
