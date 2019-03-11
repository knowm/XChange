package org.knowm.xchange.examples.bitmex.dto.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.bitmex.BitmexDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitmexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitmexDemoUtils.createExchange();
    // TradeService tradeService = exchange.getTradeService();
    MarketDataService marketDataService = exchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD, BitmexPrompt.QUARTERLY);
    OrderBook book = marketDataService.getOrderBook(CurrencyPair.BTC_USD, BitmexPrompt.QUARTERLY);

    System.out.println(trades);
    System.out.println(book);

    // tradesInfo(tradeService);
    // positionsInfo(tradeService);
  }
}
