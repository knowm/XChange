package com.xeiam.xchange.poloniex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Candlestick;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexCandlestick;
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

    Date timestamp = new Date();

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();

  }

  public static OrderBook adaptPoloniexDepth(PoloniexDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptPoloniexPublicOrders(depth.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = adaptPoloniexPublicOrders(depth.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(new Date(), asks, bids);
  }

  public static List<LimitOrder> adaptPoloniexPublicOrders(List<List<BigDecimal>> rawLevels, OrderType orderType, CurrencyPair currencyPair) {

    List<PoloniexLevel> levels = new ArrayList<PoloniexLevel>();

    for (List<BigDecimal> rawLevel : rawLevels) {
      levels.add(adaptRawPoloniexLevel(rawLevel));
    }

    List<LimitOrder> orders = new ArrayList<LimitOrder>();

    for (PoloniexLevel level : levels) {

      LimitOrder limitOrder = new LimitOrder.Builder(orderType, currencyPair).setTradableAmount(level.getAmount()).setLimitPrice(level.getLimit()).build();
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

    Trade trade = new Trade(type, poloniexTrade.getAmount(), currencyPair, poloniexTrade.getRate(), timestamp, poloniexTrade.getTradeID(), poloniexTrade.getTradeID());
    return trade;
  }

  public static Candlestick adaptPoloniexCandlestick(PoloniexCandlestick poloniexCandlestick) {

    Date date = new Date(poloniexCandlestick.getDate() * 1000L);
    BigDecimal high = poloniexCandlestick.getHigh();
    BigDecimal low = poloniexCandlestick.getLow();
    BigDecimal open = poloniexCandlestick.getOpen();
    BigDecimal close = poloniexCandlestick.getClose();
    BigDecimal volume = poloniexCandlestick.getQuoteVolume();

    return new Candlestick.Builder().setDate(date).setHigh(high).setLow(low).setOpen(open).setClose(close).setVolume(volume).build();
  }

  public static List<Candlestick> adaptPoloniexCandlesticks(List<PoloniexCandlestick> poloniexCandlesticks) {

    List<Candlestick> candlesticks = new ArrayList<Candlestick>();

    for (PoloniexCandlestick poloniexCandlestick : poloniexCandlesticks) {

      candlesticks.add(adaptPoloniexCandlestick(poloniexCandlestick));
    }

    return candlesticks;
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
    LimitOrder limitOrder = new LimitOrder.Builder(type, currencyPair).setLimitPrice(openOrder.getRate()).setTradableAmount(openOrder.getAmount()).setId(openOrder.getOrderNumber()).build();

    return limitOrder;
  }

  public static Trade adaptPoloniexUserTrade(PoloniexUserTrade userTrade, CurrencyPair currencyPair) {

    OrderType orderType = userTrade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = userTrade.getAmount();
    BigDecimal price = userTrade.getRate();
    Date date = PoloniexUtils.stringToDate(userTrade.getDate());
    String tradeId = String.valueOf(userTrade.getOrderNumber());

    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }
}
