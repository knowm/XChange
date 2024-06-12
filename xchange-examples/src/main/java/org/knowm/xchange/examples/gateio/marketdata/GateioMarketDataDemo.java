package org.knowm.xchange.examples.gateio.marketdata;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.GateioMarketInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory.GateioPublicTrade;
import org.knowm.xchange.gateio.service.GateioMarketDataServiceRaw;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class GateioMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((GateioMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USDT);
    System.out.println(ticker);

    OrderBook oderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USDT);
    System.out.println(oderBook);

    Trades tradeHistory = marketDataService.getTrades(CurrencyPair.BTC_USDT);
    System.out.println(tradeHistory);

    List<Trade> trades = tradeHistory.getTrades();
    if (trades.size() > 1) {
      Trade trade = trades.get(trades.size() - 2);
      tradeHistory =
          marketDataService.getTrades(CurrencyPair.BTC_USDT, Long.valueOf(trade.getId()));
      System.out.println(tradeHistory);
    }
  }

  private static void raw(GateioMarketDataServiceRaw marketDataService) throws IOException {

    Map<CurrencyPair, GateioMarketInfo> marketInfoMap = marketDataService.getGateioMarketInfo();
    System.out.println(marketInfoMap);

    Collection<Instrument> pairs = marketDataService.getExchangeSymbols();
    System.out.println(pairs);

    Map<CurrencyPair, Ticker> tickers = marketDataService.getGateioTickers();
    System.out.println(tickers);

    GateioTicker ticker = marketDataService.getBTERTicker("BTC", "USDT");
    System.out.println(ticker);

    GateioDepth depth = marketDataService.getBTEROrderBook("BTC", "USDT");
    System.out.println(depth);

    GateioTradeHistory tradeHistory = marketDataService.getBTERTradeHistory("BTC", "USDT");
    System.out.println(tradeHistory);

    List<GateioPublicTrade> trades = tradeHistory.getTrades();
    if (trades.size() > 1) {
      GateioPublicTrade trade = trades.get(trades.size() - 2);
      tradeHistory = marketDataService.getBTERTradeHistorySince("BTC", "USDT", trade.getTradeId());
      System.out.println(tradeHistory);
    }
  }
}
