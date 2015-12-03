package com.xeiam.xchange.examples.bter.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bter.BTERExchange;
import com.xeiam.xchange.bter.dto.marketdata.BTERDepth;
import com.xeiam.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
import com.xeiam.xchange.bter.dto.marketdata.BTERTicker;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory;
import com.xeiam.xchange.bter.dto.marketdata.BTERTradeHistory.BTERPublicTrade;
import com.xeiam.xchange.bter.service.polling.BTERPollingMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BTERMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BTERExchange.class.getName());
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((BTERPollingMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.PPC_BTC);
    System.out.println(ticker);

    OrderBook oderBook = marketDataService.getOrderBook(CurrencyPair.BTC_CNY);
    System.out.println(oderBook);

    Trades tradeHistory = marketDataService.getTrades(CurrencyPair.BTC_CNY);
    System.out.println(tradeHistory);

    List<Trade> trades = tradeHistory.getTrades();
    if (trades.size() > 1) {
      Trade trade = trades.get(trades.size() - 2);
      tradeHistory = marketDataService.getTrades(CurrencyPair.BTC_CNY, Long.valueOf(trade.getId()));
      System.out.println(tradeHistory);
    }
  }

  private static void raw(BTERPollingMarketDataServiceRaw marketDataService) throws IOException {

    Map<CurrencyPair, BTERMarketInfo> marketInfoMap = marketDataService.getBTERMarketInfo();
    System.out.println(marketInfoMap);

    Collection<CurrencyPair> pairs = marketDataService.getExchangeSymbols();
    System.out.println(pairs);

    Map<CurrencyPair, BTERTicker> tickers = marketDataService.getBTERTickers();
    System.out.println(tickers);

    BTERTicker ticker = marketDataService.getBTERTicker("PPC", "BTC");
    System.out.println(ticker);

    BTERDepth depth = marketDataService.getBTEROrderBook("BTC", "CNY");
    System.out.println(depth);

    BTERTradeHistory tradeHistory = marketDataService.getBTERTradeHistory("BTC", "CNY");
    System.out.println(tradeHistory);

    List<BTERPublicTrade> trades = tradeHistory.getTrades();
    if (trades.size() > 1) {
      BTERPublicTrade trade = trades.get(trades.size() - 2);
      tradeHistory = marketDataService.getBTERTradeHistorySince("BTC", "CNY", trade.getTradeId());
      System.out.println(tradeHistory);
    }
  }
}
