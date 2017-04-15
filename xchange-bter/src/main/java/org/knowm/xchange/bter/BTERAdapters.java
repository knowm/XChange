package org.knowm.xchange.bter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.bter.dto.BTEROrderType;
import org.knowm.xchange.bter.dto.account.BTERFunds;
import org.knowm.xchange.bter.dto.marketdata.BTERDepth;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
import org.knowm.xchange.bter.dto.marketdata.BTERPublicOrder;
import org.knowm.xchange.bter.dto.marketdata.BTERTicker;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory;
import org.knowm.xchange.bter.dto.marketdata.BTERTradeHistory.BTERPublicTrade;
import org.knowm.xchange.bter.dto.trade.BTEROpenOrder;
import org.knowm.xchange.bter.dto.trade.BTEROpenOrders;
import org.knowm.xchange.bter.dto.trade.BTERTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;

/**
 * Various adapters for converting from Bter DTOs to XChange DTOs
 */
public final class BTERAdapters {

  /**
   * private Constructor
   */
  private BTERAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, BTERTicker bterTicker) {

    BigDecimal ask = bterTicker.getSell();
    BigDecimal bid = bterTicker.getBuy();
    BigDecimal last = bterTicker.getLast();
    BigDecimal low = bterTicker.getLow();
    BigDecimal high = bterTicker.getHigh();
    BigDecimal volume = bterTicker.getVolume(currencyPair.base.getCurrencyCode());

    return new Ticker.Builder().currencyPair(currencyPair).ask(ask).bid(bid).last(last).low(low).high(high).volume(volume).build();
  }

  public static LimitOrder adaptOrder(BTERPublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<BTERPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (BTERPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(BTERDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = BTERAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = BTERAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static LimitOrder adaptOrder(BTEROpenOrder order, Collection<CurrencyPair> currencyPairs) {

    CurrencyPair possibleCurrencyPair = new CurrencyPair(order.getBuyCurrency(), order.getSellCurrency());
    if (!currencyPairs.contains(possibleCurrencyPair)) {
      BigDecimal price = order.getBuyAmount().divide(order.getSellAmount(), 8, RoundingMode.HALF_UP);
      return new LimitOrder(OrderType.ASK, order.getSellAmount(), new CurrencyPair(order.getSellCurrency(), order.getBuyCurrency()), order.getId(),
          null, price);
    } else {
      BigDecimal price = order.getSellAmount().divide(order.getBuyAmount(), 8, RoundingMode.HALF_UP);
      return new LimitOrder(OrderType.BID, order.getBuyAmount(), possibleCurrencyPair, order.getId(), null, price);
    }
  }

  public static OpenOrders adaptOpenOrders(BTEROpenOrders openOrders, Collection<CurrencyPair> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<>();
    for (BTEROpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public static OrderType adaptOrderType(BTEROrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(BTEROrderType.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static Trade adaptTrade(BTERPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getTradeId());
  }

  public static Trades adaptTrades(BTERTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();
    long lastTradeId = 0;
    for (BTERPublicTrade trade : tradeHistory.getTrades()) {
      String tradeIdString = trade.getTradeId();
      if (!tradeIdString.isEmpty()) {
        long tradeId = Long.valueOf(tradeIdString);
        if (tradeId > lastTradeId) {
          lastTradeId = tradeId;
        }
      }
      Trade adaptedTrade = adaptTrade(trade, currencyPair);
      tradeList.add(adaptedTrade);
    }

    return new Trades(tradeList, lastTradeId, TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(BTERFunds bterAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getAvailableFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      BigDecimal amount = funds.getValue();
      BigDecimal locked = bterAccountInfo.getLockedFunds().get(currency.toString());

      balances.add(new Balance(currency, null, amount, locked == null ? BigDecimal.ZERO : locked));
    }

    return new Wallet(balances);
  }

  public static UserTrades adaptUserTrades(List<BTERTrade> userTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (BTERTrade userTrade : userTrades) {
      trades.add(adaptUserTrade(userTrade));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptUserTrade(BTERTrade bterTrade) {

    OrderType orderType = adaptOrderType(bterTrade.getType());
    Date timestamp = DateUtils.fromMillisUtc(bterTrade.getTimeUnix() * 1000);
    CurrencyPair currencyPair = adaptCurrencyPair(bterTrade.getPair());

    return new UserTrade(orderType, bterTrade.getAmount(), currencyPair, bterTrade.getRate(), timestamp, bterTrade.getId(), null, null,
        (Currency) null);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(Map<CurrencyPair, BTERMarketInfo> currencyPair2BTERMarketInfoMap) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();

    for (Entry<CurrencyPair, BTERMarketInfo> entry : currencyPair2BTERMarketInfoMap.entrySet()) {

      CurrencyPair currencyPair = entry.getKey();
      BTERMarketInfo btermarketInfo = entry.getValue();

      CurrencyPairMetaData currencyPairMetaData = new CurrencyPairMetaData(btermarketInfo.getFee(), btermarketInfo.getMinAmount(), null,
          btermarketInfo.getDecimalPlaces());
      currencyPairs.put(currencyPair, currencyPairMetaData);
    }

    ExchangeMetaData exchangeMetaData = new ExchangeMetaData(currencyPairs, null, null, null, null);

    return exchangeMetaData;
  }

}
