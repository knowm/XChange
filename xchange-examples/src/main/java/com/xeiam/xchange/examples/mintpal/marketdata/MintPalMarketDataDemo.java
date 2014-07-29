package com.xeiam.xchange.examples.mintpal.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.mintpal.MintPalDemoUtils;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicOrders;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalPublicTrade;
import com.xeiam.xchange.mintpal.dto.marketdata.MintPalTicker;
import com.xeiam.xchange.mintpal.service.polling.MintPalMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class MintPalMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange mintPalExchange = MintPalDemoUtils.createExchange();
    generic(mintPalExchange);
    raw(mintPalExchange);
  }

  private static void generic(Exchange mintPal) throws IOException {

    PollingMarketDataService mintPalGenericMarketDataService = mintPal.getPollingMarketDataService();

    Collection<CurrencyPair> currencyPairs = mintPalGenericMarketDataService.getExchangeSymbols();
    System.out.println(currencyPairs);

    Ticker ticker = mintPalGenericMarketDataService.getTicker(CurrencyPair.LTC_BTC);
    System.out.println(ticker);

    OrderBook orderBook = mintPalGenericMarketDataService.getOrderBook(CurrencyPair.LTC_BTC);
    System.out.println(orderBook);

    Trades trades = mintPalGenericMarketDataService.getTrades(CurrencyPair.LTC_BTC);
    System.out.println(trades);
  }

  private static void raw(Exchange mintPal) throws IOException {

    MintPalMarketDataServiceRaw mintPalSpecificMarketDataService = (MintPalMarketDataServiceRaw) mintPal.getPollingMarketDataService();

    List<MintPalTicker> tickers = mintPalSpecificMarketDataService.getAllMintPalTickers();
    System.out.println(tickers);

    MintPalTicker ticker = mintPalSpecificMarketDataService.getMintPalTicker(CurrencyPair.LTC_BTC);
    System.out.println(ticker);

    List<MintPalPublicOrders> orders = mintPalSpecificMarketDataService.getMintPalOrders(CurrencyPair.LTC_BTC);
    System.out.println(orders);

    List<MintPalPublicTrade> trades = mintPalSpecificMarketDataService.getMintPalTrades(CurrencyPair.LTC_BTC);
    System.out.println(trades);
  }

}
