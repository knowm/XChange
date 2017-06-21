package org.knowm.xchange.livecoin;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.livecoin.service.LivecoinAsksBidsData;

public class LivecoinAdapters {

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  private LivecoinAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(LivecoinRestriction product) {
    String[] data = product.getCurrencyPair().split("\\/");
    return new CurrencyPair(data[0], data[1]);
  }

  public static OrderBook adaptOrderBook(LivecoinOrderBook book, CurrencyPair currencyPair) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> toLimitOrderList(LivecoinAsksBidsData[] levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>(levels.length);
    for (int i = 0; i < levels.length; i++) {
      LivecoinAsksBidsData ask = levels[i];

      allLevels.add(new LimitOrder(orderType, ask.getQuantity(), currencyPair, "0", null, ask.getRate()));
    }

    return allLevels;

  }

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData exchangeMetaData, List<LivecoinRestriction> products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
    for (LivecoinRestriction product : products) {
      BigDecimal minSize = product.getMinLimitQuantity().setScale(product.getPriceScale(), BigDecimal.ROUND_UNNECESSARY);

      CurrencyPair pair = adaptCurrencyPair(product);

      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);
      int priceScale = staticMetaData == null ? 8 : staticMetaData.getPriceScale();

      CurrencyPairMetaData cpmd = new CurrencyPairMetaData(null, minSize, null, priceScale);
      currencyPairs.put(pair, cpmd);

      if(!currencies.containsKey(pair.base))
        currencies.put(pair.base, null);

      if(!currencies.containsKey(pair.counter))
        currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, true);
  }

  public static Trades adaptTrades(LivecoinTrade[] coinbaseExTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(coinbaseExTrades.length);

    for (int i = 0; i < coinbaseExTrades.length; i++) {
      LivecoinTrade trade = coinbaseExTrades[i];

      OrderType type = trade.getType().equals("SELL") ? OrderType.BID : OrderType.ASK;

      Trade t = new Trade(type, trade.getQuantity(), currencyPair, trade.getPrice(), parseDate(trade.getTime()), String.valueOf(trade.getId()));
      trades.add(t);
    }

    return new Trades(trades, coinbaseExTrades[0].getId(), TradeSortType.SortByID);
  }

  private static Date parseDate(Long rawDateLong) {
    return new Date((long) rawDateLong * 1000);
  }

  public static Ticker adaptTicker(LivecoinTicker ticker, CurrencyPair currencyPair) {
    BigDecimal last = ticker.getLast();
    BigDecimal bid = ticker.getBestBid();
    BigDecimal ask = ticker.getBestAsk();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal volume = ticker.getVolume();
    
    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }
}
