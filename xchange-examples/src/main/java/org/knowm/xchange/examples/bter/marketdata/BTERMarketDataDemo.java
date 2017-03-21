package org.knowm.xchange.examples.bter.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bter.BTERExchange;
import org.knowm.xchange.bter.dto.marketdata.BTERDepth;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
import org.knowm.xchange.bter.dto.marketdata.BTERTicker;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory.BTERPublicTrade;
import org.knowm.xchange.bter.service.BTERMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BTERMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BTERExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((BTERMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

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

  private static void raw(BTERMarketDataServiceRaw marketDataService) throws IOException {

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
