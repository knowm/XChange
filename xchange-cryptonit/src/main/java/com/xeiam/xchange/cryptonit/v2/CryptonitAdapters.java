package com.xeiam.xchange.cryptonit.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrder;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitOrders;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitRate;
import com.xeiam.xchange.cryptonit.v2.dto.marketdata.CryptonitTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Cryptonit DTOs to XChange DTOs
 */

public final class CryptonitAdapters {

  /**
   * private Constructor
   */
  private CryptonitAdapters() {

  }

  /**
   * Adapts a CryptonitOrder to a LimitOrder
   * 
   * @param amount
   * @param price
   * @param currency
   * @param orderTypeString
   * @param id
   * @return
   */
  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, Date date, String id) {

    // place a limit order
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, date, price);

  }

  /**
   * Adapts CryptonitOrders to a List of LimitOrders
   * 
   * @param cryptonitOrders
   * @param currency
   * @param orderType
   * @param id
   * @return
   */
  public static List<LimitOrder> adaptOrders(CryptonitOrders cryptonitOrders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    Map<String, CryptonitOrder> orders = cryptonitOrders.getOrders();
    for (Map.Entry<String, CryptonitOrder> trade : orders.entrySet()) {
      if (orderType.equalsIgnoreCase("bid")) {
        limitOrders.add(adaptOrder(trade.getValue().getAskAmount(), trade.getValue().getAskRate(), currencyPair, orderType, DateUtils.fromMillisUtc(trade.getValue().getCreated() * 1000L), String
            .valueOf(trade.getKey())));
      }
      else {
        limitOrders.add(adaptOrder(trade.getValue().getBidAmount(), trade.getValue().getBidRate(), currencyPair, orderType, DateUtils.fromMillisUtc(trade.getValue().getCreated() * 1000L), String
            .valueOf(trade.getKey())));
      }
    }
    Collections.sort(limitOrders);

    return limitOrders;
  }

  /**
   * Adapts a CryptonitTrade to a Trade Object
   * 
   * @param cryptonitTrade A Cryptonit trade
   * @return The XChange Trade
   */
  public static Trade adaptTrade(String tradeId, CryptonitOrder cryptonitTrade, CurrencyPair currencyPair) {

    BigDecimal amount = cryptonitTrade.getBidAmount();
    BigDecimal price = cryptonitTrade.getBidRate();

    return new Trade(null, amount, currencyPair, price, DateUtils.fromMillisUtc(cryptonitTrade.getFilled() * 1000L), tradeId);
  }

  /**
   * Adapts a CryptonitTrade[] to a Trades Object
   * 
   * @param cryptonitTrades The Cryptonit trade data
   * @return The trades
   */
  public static Trades adaptTrades(CryptonitOrders cryptonitTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();

    long lastTradeId = 0;
    Map<String, CryptonitOrder> orders = cryptonitTrades.getOrders();
    for (Map.Entry<String, CryptonitOrder> trade : orders.entrySet()) {
      String tradeId = trade.getKey();
      long tradeIdAsLong = Long.valueOf(tradeId);
      if (tradeIdAsLong > lastTradeId)
        lastTradeId = tradeIdAsLong;
      tradesList.add(adaptTrade(tradeId, trade.getValue(), currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a cryptonitTicker to a Ticker Object
   * 
   * @param cryptonitTicker
   * @return
   */
  public static Ticker adaptTicker(CryptonitTicker cryptonitTicker, CurrencyPair currencyPair) {

    CryptonitRate rate = cryptonitTicker.getRate();

    BigDecimal last = rate.getLast();
    BigDecimal high = rate.getHigh();
    BigDecimal low = rate.getLow();
    BigDecimal bid = rate.getBid();
    BigDecimal ask = rate.getAsk();
    BigDecimal volume = cryptonitTicker.getVolume().getVolume(currencyPair.baseSymbol);

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withHigh(high).withLow(low).withBid(bid).withAsk(ask).withVolume(volume).build();
  }

  public static Collection<CurrencyPair> adaptCurrencyPairs(List<List<String>> tradingPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<CurrencyPair>();
    for (List<String> tradingPair : tradingPairs) {
      if (tradingPair.size() == 2) {
        CurrencyPair currencyPair = new CurrencyPair(tradingPair.get(1), tradingPair.get(0));
        currencyPairs.add(currencyPair);
      }
    }
    return currencyPairs;
  }
}
