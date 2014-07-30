package com.xeiam.xchange.coinfloor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.coinfloor.dto.streaming.CoinfloorOrder;
import com.xeiam.xchange.coinfloor.dto.streaming.account.CoinfloorAssetBalance;
import com.xeiam.xchange.coinfloor.dto.streaming.account.CoinfloorBalances;
import com.xeiam.xchange.coinfloor.dto.streaming.account.CoinfloorTradeVolume;
import com.xeiam.xchange.coinfloor.dto.streaming.marketdata.CoinfloorOrderbook;
import com.xeiam.xchange.coinfloor.dto.streaming.marketdata.CoinfloorTicker;
import com.xeiam.xchange.coinfloor.dto.streaming.trade.CoinfloorCancelOrder;
import com.xeiam.xchange.coinfloor.dto.streaming.trade.CoinfloorEstimateMarketOrder;
import com.xeiam.xchange.coinfloor.dto.streaming.trade.CoinfloorOpenOrders;
import com.xeiam.xchange.coinfloor.dto.streaming.trade.CoinfloorPlaceOrder;
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
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * @author obsessiveOrange
 */

public class CoinfloorAdapters {

  private static final ObjectMapper streamObjectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private Object cachedDataSynchronizationObject = new Object();
  private AccountInfo cachedAccountInfo;
  private Trades cachedTrades;
  private OrderBook cachedOrderBook;
  private Ticker cachedTicker;

  public Map<String, Object> adaptBalances(String data) throws ExchangeException {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorBalances rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorBalances.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }
    resultMap.put("raw", rawRetObj);

    List<Wallet> wallets = new ArrayList<Wallet>();
    List<CoinfloorAssetBalance> funds = rawRetObj.getBalances();

    for (CoinfloorAssetBalance assetBalancePair : funds) {
      String currency = assetBalancePair.getAsset().toString();
      BigDecimal balance = assetBalancePair.getBalance();

      wallets.add(new Wallet(currency, balance));
    }

    AccountInfo accountInfo = new AccountInfo(null, wallets);
    synchronized (cachedDataSynchronizationObject) {
      cachedAccountInfo = accountInfo;
    }
    resultMap.put("generic", accountInfo);

    return resultMap;
  }

  private LimitOrder adaptOrder(CoinfloorOrder order) throws ExchangeException {

    String baseCurrency = order.getBase().toString();
    String counterCurrency = order.getCounter().toString();

    OrderType type = null;
    if (order.getBaseQty().doubleValue() > 0) {
      type = OrderType.BID;
    }
    else if (order.getBaseQty().doubleValue() <= 0) {
      type = OrderType.ASK;
    }

    return new LimitOrder(type, order.getBaseQty(), new CurrencyPair(baseCurrency, counterCurrency), String.valueOf(order.getId()), new Date(order.getTime()), order.getPrice());
  }

  public Map<String, Object> adaptOpenOrders(String data) throws ExchangeException {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorOpenOrders rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorOpenOrders.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }
    resultMap.put("raw", rawRetObj);

    List<LimitOrder> openOrdersList = new ArrayList<LimitOrder>();
    List<CoinfloorOrder> orders = rawRetObj.getOrders();

    for (CoinfloorOrder order : orders) {
      openOrdersList.add(adaptOrder(order));
    }

    OpenOrders openOrders = new OpenOrders(openOrdersList);
    resultMap.put("generic", openOrders);

    return resultMap;
  }

  public Map<String, Object> adaptPlaceOrder(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorPlaceOrder rawRetObj;
    Map<String, Object> jsonData;
    try {
      jsonData = streamObjectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
      });
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorPlaceOrder.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    resultMap.put("generic", jsonData.get("id").toString());
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptCancelOrder(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorCancelOrder rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorCancelOrder.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    resultMap.put("generic", true);
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptTradeVolume(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorTradeVolume rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorTradeVolume.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    resultMap.put("generic", rawRetObj.getAssetVol());
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  // No meaningful generic form availiable - raw and generic output will be the same.
  public Map<String, Object> adaptEstimateMarketOrder(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorEstimateMarketOrder rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorEstimateMarketOrder.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    resultMap.put("generic", rawRetObj);
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptTicker(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorTicker rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorTicker.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    // String tradableIdentifier, BigDecimal last, BigDecimal bid, BigDecimal ask, BigDecimal high, BigDecimal low, BigDecimal volume, Date timestamp
    // base & counter currencies hard coded in; no way to make it dynamic with return data - may change over time.
    BigDecimal last = rawRetObj.getLast();
    BigDecimal bid = rawRetObj.getBid();
    BigDecimal ask = rawRetObj.getAsk();
    BigDecimal low = rawRetObj.getLow();
    BigDecimal high = rawRetObj.getHigh();
    Ticker genericTicker =
        new Ticker.TickerBuilder().withVolume(rawRetObj.getVolume()).withAsk(ask).withCurrencyPair(new CurrencyPair("BTC", "GBP")).withBid(bid).withHigh(high).withLow(low).withLast(last).build();

    synchronized (cachedDataSynchronizationObject) {
      cachedTicker = genericTicker;
    }

    resultMap.put("generic", genericTicker);
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptTickerUpdate(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorTicker rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorTicker.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }
    Ticker genericTicker;

    synchronized (cachedDataSynchronizationObject) {
      // base & counter currencies hard coded in; no way to make it dynamic with return data - may change over time.
      BigDecimal last = (rawRetObj.getLast().doubleValue() == 0 ? cachedTicker.getLast() : rawRetObj.getLast());
      BigDecimal bid = (rawRetObj.getBid().doubleValue() == 0 ? cachedTicker.getBid() : rawRetObj.getBid());
      BigDecimal ask = (rawRetObj.getAsk().doubleValue() == 0 ? cachedTicker.getAsk() : rawRetObj.getAsk());
      BigDecimal low = (rawRetObj.getLow().doubleValue() == 0 ? cachedTicker.getLow() : rawRetObj.getLow());
      BigDecimal high = (rawRetObj.getHigh().doubleValue() == 0 ? cachedTicker.getHigh() : rawRetObj.getHigh());
      BigDecimal volume = (rawRetObj.getVolume().doubleValue() == 0 ? cachedTicker.getVolume() : rawRetObj.getVolume());

      genericTicker =
          new Ticker.TickerBuilder().withCurrencyPair(new CurrencyPair(rawRetObj.getBase().toString(), rawRetObj.getCounter().toString())).withLast(last).withBid(bid).withAsk(ask).withLow(low)
              .withHigh(high).withVolume(volume).build();
      cachedTicker = genericTicker;
    }

    resultMap.put("generic", genericTicker);
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptOrders(String data) throws ExchangeException {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorOrderbook rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorOrderbook.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }
    resultMap.put("raw", rawRetObj);
    OrderBook orderbook;

    synchronized (cachedDataSynchronizationObject) {
      List<LimitOrder> bidList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getBids());
      List<LimitOrder> askList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getAsks());
      List<CoinfloorOrder> orders = rawRetObj.getOrders();

      if (orders != null) {
        for (CoinfloorOrder order : orders) {
          if (order.getBaseQty().doubleValue() > 0) {
            bidList.add(adaptOrder(order));
          }
          else {
            askList.add(adaptOrder(order));
          }
        }
      }

      orderbook = new OrderBook(new Date(), askList, bidList);
      cachedOrderBook = orderbook;
    }
    resultMap.put("generic", orderbook);

    return resultMap;
  }

  public Map<String, Object> adaptOrderOpened(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorOrder rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorOrder.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    synchronized (cachedDataSynchronizationObject) {
      List<LimitOrder> bidList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getBids());
      List<LimitOrder> askList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getAsks());
      if (rawRetObj.getBaseQty().doubleValue() > 0) {
        bidList.add(adaptOrder(rawRetObj));
      }
      else {
        askList.add(adaptOrder(rawRetObj));
      }
      cachedOrderBook = new OrderBook(new Date(), askList, bidList);
    }

    resultMap.put("generic", adaptOrder(rawRetObj));
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptOrderClosed(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorOrder rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorOrder.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    synchronized (cachedDataSynchronizationObject) {
      List<LimitOrder> bidList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getBids());
      List<LimitOrder> askList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getAsks());
      if (rawRetObj.getBaseQty().doubleValue() > 0) {
        for (int i = 0; i < bidList.size(); i++) {
          if (bidList.get(i).getId().equals(String.valueOf(rawRetObj.getId()))) {
            bidList.remove(i);
            break;
          }
        }
      }
      else {
        for (int i = 0; i < askList.size(); i++) {
          if (askList.get(i).getId().equals(String.valueOf(rawRetObj.getId()))) {
            askList.remove(i);
            break;
          }
        }
      }
      cachedOrderBook = new OrderBook(new Date(), askList, bidList);
    }

    resultMap.put("generic", adaptOrder(rawRetObj));
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptOrdersMatched(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorOrder rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorOrder.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    Trade trade;

    synchronized (cachedDataSynchronizationObject) {
      List<LimitOrder> bidList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getBids());
      List<LimitOrder> askList = (cachedOrderBook == null ? new ArrayList<LimitOrder>() : cachedOrderBook.getAsks());
      if (rawRetObj.getBidId() != 0) {
        for (int i = 0; i < bidList.size(); i++) {
          if (bidList.get(i).getId().equals(String.valueOf(rawRetObj.getBidId()))) {
            bidList.remove(i);
            break;
          }
        }
      }
      if (rawRetObj.getAskId() != 0) {
        for (int i = 0; i < askList.size(); i++) {
          if (askList.get(i).getId().equals(String.valueOf(rawRetObj.getAskId()))) {
            askList.remove(i);
            break;
          }
        }
      }
      cachedOrderBook = new OrderBook(new Date(), askList, bidList);

      OrderType type = (rawRetObj.getBidId() > rawRetObj.getAskId() ? OrderType.BID : OrderType.ASK);
      BigDecimal limitPrice = rawRetObj.getPrice();

      trade =
          new Trade(type, rawRetObj.getBaseQty(), new CurrencyPair(rawRetObj.getBase().toString(), rawRetObj.getCounter().toString()), limitPrice, new Date(), String.valueOf(rawRetObj.getId()), null);

      List<Trade> newTradesList = (cachedTrades == null ? new ArrayList<Trade>() : cachedTrades.getTrades());
      newTradesList.add(trade);

      Trades newCachedTrades = new Trades(newTradesList, TradeSortType.SortByID);
      cachedTrades = newCachedTrades;
    }

    resultMap.put("generic", trade);
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  public Map<String, Object> adaptBalancesChanged(String data) {

    Map<String, Object> resultMap = new HashMap<String, Object>();

    CoinfloorAssetBalance rawRetObj;
    try {
      rawRetObj = streamObjectMapper.readValue(data, CoinfloorAssetBalance.class);
    } catch (IOException e) {
      throw new ExchangeException("JSON parse error", e);
    }

    List<Wallet> newWallets = new ArrayList<Wallet>();
    AccountInfo accountInfo;

    synchronized (cachedDataSynchronizationObject) {
      if (cachedAccountInfo == null) {
        String currency = rawRetObj.getAsset().toString();
        BigDecimal balance = rawRetObj.getBalance();

        newWallets.add(new Wallet(currency, balance));
      }
      else {
        List<Wallet> oldWallets = cachedAccountInfo.getWallets();
        for (Wallet wallet : oldWallets) {
          if (wallet.getCurrency().equals(rawRetObj.getAsset())) {
            String currency = rawRetObj.getAsset().toString();
            BigDecimal balance = rawRetObj.getBalance();

            newWallets.add(new Wallet(currency, balance));
          }
          else {
            newWallets.add(wallet);
          }
        }
      }

      accountInfo = new AccountInfo(null, newWallets);
      cachedAccountInfo = accountInfo;
    }

    resultMap.put("generic", accountInfo);
    resultMap.put("raw", rawRetObj);

    return resultMap;
  }

  /**
   * Experimental: USE WITH CAUTION.
   * Adapters take every "BalancesUpdated" event, update local AccountInfo object with said new balance.
   * This method will return that cached AccountInfo object.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if getBalances method has not yet been called, or response has not been recieved.
   */
  public AccountInfo getCachedAccountInfo() {

    synchronized (cachedDataSynchronizationObject) {
      if (cachedAccountInfo == null) {
        throw new ExchangeException("getBalances method has not been called yet, or balance data has not been recieved!");
      }
      return cachedAccountInfo;
    }
  }

  /**
   * Experimental: USE WITH CAUTION.
   * Adapters take every "OrderOpened," "OrdersMatched," or "OrderClosed" event, update local Orderbook object.
   * This method will return that cached Orderbook object.
   * Notes: Will not survive program restarts, instantiated upon class instantiation with NO WALLETS.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if watchOrders method has not been called.
   */
  public OrderBook getCachedOrderBook() {

    synchronized (cachedDataSynchronizationObject) {
      if (cachedOrderBook == null) {
        throw new ExchangeException("watchOrders method has not been called yet!");
      }
      return cachedOrderBook;
    }
  }

  /**
   * Experimental: USE WITH CAUTION.
   * Adapters cache every "OrdersMatched" event, add the trade to a local Trades object.
   * This method will return that cached Trades object.
   * Notes: Will not survive program restarts, will only cache user's transactions, unless
   * \tWatchOrders method is called, in which case it will cache ALL transctions happening on that market.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if watchOrders method has not been called, or no trades have occurred.
   */
  public Trades getCachedTrades() {

    synchronized (cachedDataSynchronizationObject) {
      if (cachedTrades == null) {
        throw new ExchangeException("watchOrders method has not been called yet, or no trades have occurred!");
      }
      return cachedTrades;
    }
  }

  /**
   * Experimental: USE WITH CAUTION.
   * Adapters cache every "OrdersMatched" event, add the trade to a local Trades object.
   * This method will return that cached Trades object.
   * Notes: Will not survive program restarts, will only cache user's transactions, unless
   * \tWatchOrders method is called, in which case it will cache ALL transctions happening on that market.
   * 
   * @return Trades object representing all OrdersMatched trades recieved.
   * @throws ExchangeException if watchOrders method has not been called, or no trades have occurred.
   */
  public Ticker getCachedTicker() {

    synchronized (cachedDataSynchronizationObject) {
      if (cachedTicker == null) {
        throw new ExchangeException("watchTicker method has not been called yet, or data has not been recieved");
      }
      return cachedTicker;
    }
  }
}
