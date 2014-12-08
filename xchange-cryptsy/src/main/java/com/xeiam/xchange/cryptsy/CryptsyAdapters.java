package com.xeiam.xchange.cryptsy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.cryptsy.dto.CryptsyOrder;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyAccountInfo;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyAccountInfoReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyBuyOrder;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyGetMarketsReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyMarketTradesReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBook;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyOrderBookReturn;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicMarketData;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrder;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicOrderbook;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsyPublicTrade;
import com.xeiam.xchange.cryptsy.dto.marketdata.CryptsySellOrder;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyOpenOrders;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyOpenOrdersReturn;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyTradeHistory;
import com.xeiam.xchange.cryptsy.dto.trade.CryptsyTradeHistoryReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * @author ObsessiveOrange
 */
public final class CryptsyAdapters {

  public static final Logger log = LoggerFactory.getLogger(CryptsyAdapters.class);

  /**
   * Adapt CryptsyOrderBookReturn DTO to XChange OrderBook DTO
   * 
   * @param cryptsyOrderBookReturn Raw returned data from Cryptsy, CryptsyOrderBookReturn DTO
   * @param currencyPair The market for which this orderbook belongs to (Not given in Cryptsy response)
   * @return Standard XChange OrderBook DTO
   */
  public static OrderBook adaptOrderBook(CryptsyOrderBookReturn cryptsyOrderBookReturn, CurrencyPair currencyPair) {

    CryptsyOrderBook cryptsyOrderBook = cryptsyOrderBookReturn.getReturnValue();

    List<LimitOrder> asks = CryptsyAdapters.adaptSellOrders(cryptsyOrderBook.sellOrders(), currencyPair);
    List<LimitOrder> bids = CryptsyAdapters.adaptBuyOrders(cryptsyOrderBook.buyOrders(), currencyPair);

    return new OrderBook(new Date(), asks, bids);
  }

  /**
   * Private method for adapting a list of CryptsySellOrders to LimitOrders
   * 
   * @param sellOrders List of CryptsySellOrders
   * @param currencyPair The market for which this list belongs to (Usually not given in Cryptsy response)
   * @return Standard XChange LimitOrder DTO
   */
  private static List<LimitOrder> adaptSellOrders(List<CryptsySellOrder> sellOrders, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    if (sellOrders != null) {
      for (CryptsySellOrder order : sellOrders) {

        limitOrders.add(new LimitOrder(OrderType.ASK, order.getQuantity(), currencyPair, null, null, order.getSellPrice()));
      }
    }

    return limitOrders;
  }

  /**
   * Private method for adapting a list of CryptsyBuyOrders to LimitOrders
   * 
   * @param buyOrders List of CryptsyBuyOrders
   * @param currencyPair The market for which this list belongs to (Usually not given in Cryptsy response)
   * @return Standard XChange LimitOrder DTO
   */
  private static List<LimitOrder> adaptBuyOrders(List<CryptsyBuyOrder> buyOrders, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    if (buyOrders != null) {
      for (CryptsyBuyOrder order : buyOrders) {

        limitOrders.add(new LimitOrder(OrderType.BID, order.getQuantity(), currencyPair, null, null, order.getBuyPrice()));
      }
    }

    return limitOrders;
  }

  public static List<OrderBook> adaptPublicOrderBooks(Map<Integer, CryptsyPublicOrderbook> cryptsyOrderBooks) {

    Date timestamp = new Date();
    List<OrderBook> orderBooks = new ArrayList<OrderBook>();
    for (CryptsyPublicOrderbook cryptsyOrderBook : cryptsyOrderBooks.values()) {
      CurrencyPair currencyPair = adaptCurrencyPair(cryptsyOrderBook.getLabel());
      List<LimitOrder> asks = adaptPublicOrders(cryptsyOrderBook.getSellOrders(), OrderType.ASK, currencyPair);
      List<LimitOrder> bids = adaptPublicOrders(cryptsyOrderBook.getBuyOrders(), OrderType.BID, currencyPair);

      orderBooks.add(new OrderBook(timestamp, asks, bids));
    }

    return orderBooks;
  }

  private static List<LimitOrder> adaptPublicOrders(List<CryptsyPublicOrder> orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    if (orders != null) {
      for (CryptsyPublicOrder order : orders) {

        limitOrders.add(new LimitOrder(orderType, order.getQuantity(), currencyPair, null, null, order.getPrice()));
      }
    }

    return limitOrders;
  }

  /**
   * Adapts CryptsyMarketTradesReturn DTO to XChange standard Trades DTO
   * 
   * @param cryptsyTrades Raw returned data from Cryptsy, CryptsyMarketTradesReturn DTO
   * @param currencyPair The market for which this list belongs to (Usually not given in Cryptsy response)
   * @return Standard XChange Trades DTO
   */
  public static Trades adaptTrades(CryptsyMarketTradesReturn cryptsyTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (CryptsyOrder trade : cryptsyTrades.getReturnValue()) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId)
        lastTradeId = tradeId;
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Private method for adapting a CryptsyOrder to Trade
   * 
   * @param order CryptsyOrder DTO to be adapted
   * @param currencyPair The market for which this CryptsyOrder belongs to (Usually not given in Cryptsy response)
   * @return Standard XChange Trade DTO
   */
  private static Trade adaptTrade(CryptsyOrder order, CurrencyPair currencyPair) {

    OrderType orderType = order.getType() == CryptsyOrderType.Buy ? OrderType.BID : OrderType.ASK;

    return new Trade(orderType, order.getQuantity(), currencyPair, order.getPrice(), order.getTime(), String.valueOf(order.getTradeId()));
  }

  public static Map<CurrencyPair, Trades> adaptPublicTrades(Map<Integer, CryptsyPublicMarketData> cryptsyMarketData) {

    Map<CurrencyPair, Trades> trades = new HashMap<CurrencyPair, Trades>();
    for (CryptsyPublicMarketData cryptsyMarketDataEntry : cryptsyMarketData.values()) {
      CurrencyPair currencyPair = adaptCurrencyPair(cryptsyMarketDataEntry.getLabel());
      List<Trade> tradesList = new ArrayList<Trade>();
      long lastTradeId = 0;
      List<CryptsyPublicTrade> recentTrades = cryptsyMarketDataEntry.getRecentTrades();
      if (recentTrades != null) {
        for (CryptsyPublicTrade trade : recentTrades) {
          long tradeId = trade.getId();
          if (tradeId > lastTradeId)
            lastTradeId = tradeId;
          tradesList.add(adaptTrade(trade, currencyPair));
        }
      }
      trades.put(currencyPair, new Trades(tradesList, lastTradeId, TradeSortType.SortByID));
    }

    return trades;
  }

  private static Trade adaptTrade(CryptsyPublicTrade trade, CurrencyPair currencyPair) {

    OrderType type = null;

    if (trade.getType() != null) {
      type = trade.getType().equalsIgnoreCase("Buy") ? OrderType.BID : OrderType.ASK;
    }

    return new Trade(type, trade.getQuantity(), currencyPair, trade.getPrice(), trade.getTime(), String.valueOf(trade.getId()));
  }

  /**
   * Adapts CryptsyGetMarketsReturn DTO to XChange standard Ticker DTO
   * Note: Cryptsy does not natively have a Ticker method, so getMarkets function will have to be called to get summary data
   * 
   * @param marketsReturnData Raw returned data from Cryptsy, CryptsyGetMarketsReturn DTO
   * @param currencyPair The market for which this CryptsyGetMarketsReturn belongs to (Usually not given in Cryptsy response)
   * @return Standard XChange Ticker DTO
   */
  public static Ticker adaptTicker(CryptsyGetMarketsReturn marketsReturnData, CurrencyPair currencyPair) {

    List<CryptsyMarketData> marketData = marketsReturnData.getReturnValue();

    String label = currencyPair.toString().replace("_", "/");

    CryptsyMarketData targetMarket = null;
    for (CryptsyMarketData currMarket : marketData) {
      if (currMarket.getLabel().equalsIgnoreCase(label)) {
        targetMarket = currMarket;
        break;
      }
    }

    BigDecimal last = targetMarket.getLast();
    BigDecimal bid = null;
    BigDecimal ask = null;
    BigDecimal high = targetMarket.getHigh();
    BigDecimal low = targetMarket.getLow();
    BigDecimal volume = targetMarket.get24hVolume();
    Date timestamp = new Date();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp).build();
  }

  public static List<Ticker> adaptPublicTickers(Map<Integer, CryptsyPublicMarketData> marketsReturnData) {

    List<Ticker> tickers = new ArrayList<Ticker>();
    for (CryptsyPublicMarketData publicMarketData : marketsReturnData.values()) {
      tickers.add(adaptPublicTicker(publicMarketData));
    }
    return tickers;
  }

  /**
   * Adapts CryptsyPublicMarketData DTO to XChange standard Ticker DTO
   * Note: Cryptsy does not natively have a Ticker method, so getCryptsyMarketData function will have to be called to get summary data
   * 
   * @param publicMarketData Raw returned data from Cryptsy, CryptsyGetMarketsReturn DTO
   * @param currencyPair The market for which this CryptsyGetMarketsReturn belongs to (Usually not given in Cryptsy response)
   * @return Standard XChange Ticker DTO
   */
  public static Ticker adaptPublicTicker(CryptsyPublicMarketData publicMarketData) {

    List<CryptsyPublicTrade> recentTrades = publicMarketData.getRecentTrades();
    BigDecimal last = (recentTrades != null && recentTrades.size() > 0) ? recentTrades.get(0).getPrice() : null;
    List<CryptsyPublicOrder> bids = publicMarketData.getBuyOrders();
    BigDecimal bid = (bids != null && bids.size() > 0) ? bids.get(0).getPrice() : null;
    List<CryptsyPublicOrder> asks = publicMarketData.getSellOrders();
    BigDecimal ask = (asks != null && asks.size() > 0) ? asks.get(0).getPrice() : null;
    BigDecimal high = null;
    BigDecimal low = null;
    BigDecimal volume = publicMarketData.getVolume();
    Date timestamp = new Date();

    return new Ticker.Builder().currencyPair(adaptCurrencyPair(publicMarketData)).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(
            timestamp).build();
  }

  /**
   * Adapts CryptsyAccountInfoReturn DTO to XChange standard AccountInfo DTO
   * 
   * @param cryptsyAccountInfoReturn Raw returned data from Cryptsy, CryptsyAccountInfoReturn DTO
   * @return Standard XChange AccountInfo DTO
   */
  public static AccountInfo adaptAccountInfo(CryptsyAccountInfoReturn cryptsyAccountInfoReturn) {

    CryptsyAccountInfo cryptsyAccountInfo = cryptsyAccountInfoReturn.getReturnValue();

    List<Wallet> wallets = new ArrayList<Wallet>();
    Map<String, BigDecimal> funds = cryptsyAccountInfo.getAvailableFunds();

    for (String lcCurrency : funds.keySet()) {
      String currency = lcCurrency.toUpperCase();

      wallets.add(new Wallet(currency, funds.get(lcCurrency)));
    }
    return new AccountInfo(null, wallets);
  }

  /**
   * Adapts CryptsyOpenOrdersReturn DTO to XChange standard OpenOrd ers DTO
   * 
   * @param openOrdersReturnValue Raw returned data from Cryptsy, CryptsyOpenOrdersReturn DTO
   * @return Standard XChange OpenOrders DTO
   */
  public static OpenOrders adaptOpenOrders(CryptsyOpenOrdersReturn openOrdersReturnValue) {

    List<CryptsyOpenOrders> cryptsyOpenOrders = openOrdersReturnValue.getReturnValue();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    if (cryptsyOpenOrders != null) {
      for (CryptsyOpenOrders order : cryptsyOpenOrders) {

        OrderType orderType = order.getTradeType() == CryptsyOrderType.Buy ? OrderType.BID : OrderType.ASK;

        limitOrders.add(new LimitOrder(orderType, order.getQuantityRemaining(), CryptsyCurrencyUtils.convertToCurrencyPair(order.getMarketId()), String.valueOf(order.getOrderId()), order
            .getTimestamp(), order.getPrice()));

      }
    }
    return new OpenOrders(limitOrders);
  }

  /**
   * Adapts CryptsyTradeHistoryReturn DTO to XChange standard Trades DTO
   * 
   * @param tradeHistoryReturnData Raw returned data from Cryptsy, CryptsyTradeHistoryReturn DTO
   * @return Standard XChange Trades DTO
   */
  public static UserTrades adaptTradeHistory(CryptsyTradeHistoryReturn tradeHistoryReturnData) {

    List<CryptsyTradeHistory> cryptsyTradeHistory = tradeHistoryReturnData.getReturnValue();

    List<UserTrade> trades = new ArrayList<UserTrade>();
    if (cryptsyTradeHistory != null) {
      for (CryptsyTradeHistory trade : cryptsyTradeHistory) {
        OrderType tradeType = trade.getTradeType() == CryptsyOrderType.Buy ? OrderType.BID : OrderType.ASK;
        CurrencyPair currencyPair = CryptsyCurrencyUtils.convertToCurrencyPair(trade.getMarketId());

        trades.add(new UserTrade(tradeType, trade.getQuantity(), currencyPair, trade.getPrice(), trade.getTimestamp(), String.valueOf(trade.getTradeId()), String.valueOf(trade.getOrderId()), trade.getFee(), currencyPair.counterSymbol));
      }
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts CryptsyPublicMarketData DTO's to List<CurrencyPair>
   * Used mainly by CryptsyBasePollingService to update list of CurrencyPairs and Markets
   * 
   * @param cryptsyPublicMarketData returned data from Cryptsy
   * @return Collection<CurrencyPair>
   */
  public static Collection<CurrencyPair> adaptCurrencyPairs(Map<Integer, CryptsyPublicMarketData> cryptsyPublicMarketData) {

    Set<CurrencyPair> pairs = new HashSet<CurrencyPair>();
    for (CryptsyPublicMarketData cryptsyMarket : cryptsyPublicMarketData.values()) {
      pairs.add(adaptCurrencyPair(cryptsyMarket));
    }

    return pairs;
  }

  public static CurrencyPair adaptCurrencyPair(CryptsyPublicMarketData cryptsyPublicMarketData) {

    return new CurrencyPair(cryptsyPublicMarketData.getPrimaryCurrencyCode(), cryptsyPublicMarketData.getSecondaryCurrencyCode());
  }

  public static CurrencyPair adaptCurrencyPair(String cryptsyLabel) {

    String[] marketCurrencies = cryptsyLabel.split("/");
    return new CurrencyPair(marketCurrencies[0], marketCurrencies[1]);
  }

  /**
   * Adapts CryptsyPublicMarketData DTO's to HashMap[2] of markets, keyed by marketId, and Name
   * Used mainly by CryptsyBasePollingService to update list of CurrencyPairs and Markets
   * 
   * @param cryptsyPublicMarketData returned data from Cryptsy
   * @return HashMap[2] of markets, keyed by marketId, and Name
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static HashMap[] adaptMarketSets(Map<Integer, CryptsyPublicMarketData> cryptsyPublicMarketData) {

    HashMap[] marketSets = new HashMap[2];

    marketSets[0] = new HashMap<Integer, CurrencyPair>();
    marketSets[1] = new HashMap<CurrencyPair, Integer>();

    for (CryptsyPublicMarketData currMarketData : cryptsyPublicMarketData.values()) {
      CurrencyPair currencyPair = adaptCurrencyPair(currMarketData.getLabel());

      ((HashMap<Integer, CurrencyPair>) marketSets[0]).put(currMarketData.getMarketId(), currencyPair);
      ((HashMap<CurrencyPair, Integer>) marketSets[1]).put(currencyPair, currMarketData.getMarketId());
    }

    return marketSets;
  }

  public static Map<Integer, CryptsyPublicMarketData> adaptPublicMarketDataMap(Map<String, CryptsyPublicMarketData> rawData) {

    Map<Integer, CryptsyPublicMarketData> resultData = new HashMap<Integer, CryptsyPublicMarketData>();
    for (CryptsyPublicMarketData cryptsyPublicMarketData : rawData.values()) {
      resultData.put(cryptsyPublicMarketData.getMarketId(), cryptsyPublicMarketData);
    }
    return resultData;
  }

  public static Map<Integer, CryptsyPublicOrderbook> adaptPublicOrderBookMap(Map<String, CryptsyPublicOrderbook> rawData) {

    Map<Integer, CryptsyPublicOrderbook> resultData = new HashMap<Integer, CryptsyPublicOrderbook>();
    for (CryptsyPublicOrderbook cryptsyPublicOrderBook : rawData.values()) {
      resultData.put(cryptsyPublicOrderBook.getMarketId(), cryptsyPublicOrderBook);
    }
    return resultData;
  }
}
