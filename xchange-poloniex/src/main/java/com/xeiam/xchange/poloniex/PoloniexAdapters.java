package com.xeiam.xchange.poloniex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexDepth;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexLevel;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexTicker;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexUserTrade;

/**
 * @author Zach Holmes
 */

public class PoloniexAdapters {

  public static Ticker adaptPoloniexTicker(PoloniexTicker poloniexTicker, CurrencyPair currencyPair) {

    PoloniexMarketData marketData = poloniexTicker.getPoloniexMarketData();

    BigDecimal last = marketData.getLast();
    BigDecimal bid = marketData.getHighestBid();
    BigDecimal ask = marketData.getLowestAsk();
    BigDecimal high = null;
    BigDecimal low = null;
    BigDecimal volume = marketData.getQuoteVolume();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();

  }

  public static OrderBook adaptPoloniexDepth(PoloniexDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptPoloniexPublicOrders(depth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptPoloniexPublicOrders(depth.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> adaptPoloniexPublicOrders(List<List<BigDecimal>> rawLevels, OrderType orderType, CurrencyPair currencyPair) {

    List<PoloniexLevel> levels = new ArrayList<PoloniexLevel>();

    for (List<BigDecimal> rawLevel : rawLevels) {
      levels.add(adaptRawPoloniexLevel(rawLevel));
    }

    List<LimitOrder> orders = new ArrayList<LimitOrder>();

    for (PoloniexLevel level : levels) {

      LimitOrder limitOrder = new LimitOrder.Builder(orderType, currencyPair).tradableAmount(level.getAmount()).limitPrice(level.getLimit()).build();
      orders.add(limitOrder);
    }
    return orders;
  }

  public static PoloniexLevel adaptRawPoloniexLevel(List<BigDecimal> level) {

    PoloniexLevel poloniexLevel = new PoloniexLevel(level.get(1), level.get(0));
    return poloniexLevel;
  }

  public static Trades adaptPoloniexPublicTrades(PoloniexPublicTrade[] poloniexPublicTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();

    for (PoloniexPublicTrade poloniexTrade : poloniexPublicTrades) {
      trades.add(adaptPoloniexPublicTrade(poloniexTrade, currencyPair));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Trade adaptPoloniexPublicTrade(PoloniexPublicTrade poloniexTrade, CurrencyPair currencyPair) {

    OrderType type = poloniexTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
    Date timestamp = PoloniexUtils.stringToDate(poloniexTrade.getDate());

    Trade trade = new Trade(type, poloniexTrade.getAmount(), currencyPair, poloniexTrade.getRate(), timestamp, poloniexTrade.getTradeID());
    return trade;
  }

  public static List<Wallet> adaptPoloniexBalances(HashMap<String, String> poloniexBalances) {

    List<Wallet> wallets = new ArrayList<Wallet>();

    for (String currency : poloniexBalances.keySet()) {
      wallets.add(new Wallet(currency, new BigDecimal(poloniexBalances.get(currency))));
    }

    return wallets;
  }

  public static OpenOrders adaptPoloniexOpenOrders(HashMap<String, PoloniexOpenOrder[]> poloniexOpenOrders) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
    for (String pairString : poloniexOpenOrders.keySet()) {
      CurrencyPair currencyPair = PoloniexUtils.toCurrencyPair(pairString);

      for (PoloniexOpenOrder openOrder : poloniexOpenOrders.get(pairString)) {

        openOrders.add(adaptPoloniexOpenOrder(openOrder, currencyPair));
      }
    }

    return new OpenOrders(openOrders);
  }

  public static LimitOrder adaptPoloniexOpenOrder(PoloniexOpenOrder openOrder, CurrencyPair currencyPair) {

    OrderType type = openOrder.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    Date timestamp = PoloniexUtils.stringToDate(openOrder.getDate());
    LimitOrder limitOrder = new LimitOrder.Builder(type, currencyPair).limitPrice(openOrder.getRate()).tradableAmount(openOrder.getAmount())
        .id(openOrder.getOrderNumber()).timestamp(timestamp).build();

    return limitOrder;
  }

  public static UserTrade adaptPoloniexUserTrade(PoloniexUserTrade userTrade, CurrencyPair currencyPair) {

    OrderType orderType = userTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = userTrade.getAmount();
    BigDecimal price = userTrade.getRate();
    Date date = PoloniexUtils.stringToDate(userTrade.getDate());
    String tradeId = String.valueOf(userTrade.getTradeID());
    String orderId = String.valueOf(userTrade.getOrderNumber());

    // Poloniex returns fee as a multiplier, e.g. a 0.2% fee is 0.002
    BigDecimal feeAmount = amount.multiply(price).multiply(userTrade.getFee());

    return new UserTrade(orderType, amount, currencyPair, price, date, tradeId, orderId, feeAmount, currencyPair.counterSymbol);
  }
}
