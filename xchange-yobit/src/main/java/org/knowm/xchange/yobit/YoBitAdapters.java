package org.knowm.xchange.yobit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.yobit.dto.marketdata.YoBitAsksBidsData;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBook;
import org.knowm.xchange.yobit.dto.marketdata.YoBitPair;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTicker;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickerReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrade;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;

public class YoBitAdapters {

  public static CurrencyPair adaptCurrencyPair(String pair) {
    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0].toUpperCase(), currencies[1].toUpperCase());
  }

  public static OrderBook adaptOrderBook(YoBitOrderBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData, YoBitInfo products) {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (Entry<CurrencyPair, YoBitPair> ee : products.getPairs().getPrice().entrySet()) {
      BigDecimal minSize = ee.getValue().getMin_amount();
      CurrencyPairMetaData cpmd = new CurrencyPairMetaData(ee.getValue().getFee(), minSize, null, 8);
      CurrencyPair pair = ee.getKey();
      currencyPairs.put(pair, cpmd);
      currencies.put(pair.base, null);
      currencies.put(pair.counter, null);
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  private static List<LimitOrder> toLimitOrderList(List<YoBitAsksBidsData> levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>(levels.size());
    for (int i = 0; i < levels.size(); i++) {
      YoBitAsksBidsData ask = levels.get(i);
      if (ask != null) {
        allLevels.add(new LimitOrder(orderType, ask.getQuantity(), currencyPair, "0", null, ask.getRate()));
      }
    }

    return allLevels;

  }

  public static Trades adaptTrades(YoBitTrades coinbaseTrades, CurrencyPair currencyPair) {

    List<YoBitTrade> ctrades = coinbaseTrades.getTrades();

    List<Trade> trades = new ArrayList<>(ctrades.size());

    int lastTrade = 0;

    for (int i = 0; i < ctrades.size(); i++) {
      YoBitTrade trade = ctrades.get(i);

      OrderType type = trade.getType().equals("bid") ? OrderType.BID : OrderType.ASK;

      Trade t = new Trade(type, trade.getAmount(), currencyPair, trade.getPrice(), parseDate(trade.getTimestamp()), String.valueOf(trade.getTid()));
      trades.add(t);
      lastTrade = i;
    }

    return new Trades(trades, ctrades.get(lastTrade).getTid(), TradeSortType.SortByID);
  }

  private static Date parseDate(Long rawDateLong) {
    return new java.util.Date((long) rawDateLong * 1000);
  }

  public static Ticker adaptTicker(YoBitTickerReturn tickerReturn, CurrencyPair currencyPair) {
    YoBitTicker ticker = tickerReturn.getTicker();
    Ticker.Builder builder = new Ticker.Builder();
    
    builder.currencyPair(currencyPair);
    builder.last(ticker.getLast());
    builder.bid(ticker.getBuy());
    builder.ask(ticker.getSell());
    builder.high(ticker.getHigh());
    builder.low(ticker.getLow());
    builder.volume(ticker.getVolCur());
    builder.timestamp(new Date(ticker.getUpdated() * 1000L));
    
    return builder.build();
  }
}
