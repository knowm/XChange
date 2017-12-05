package org.knowm.xchange.gateio;

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

import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.account.GateioFunds;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.BTERMarketInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioPublicOrder;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrder;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioTrade;
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
public final class GateioAdapters {

  /**
   * private Constructor
   */
  private GateioAdapters() {

  }

  public static CurrencyPair adaptCurrencyPair(String pair) {

    final String[] currencies = pair.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, GateioTicker gateioTicker) {

    BigDecimal ask = gateioTicker.getSell();
    BigDecimal bid = gateioTicker.getBuy();
    BigDecimal last = gateioTicker.getLast();
    BigDecimal low = gateioTicker.getLow();
    BigDecimal high = gateioTicker.getHigh();
    BigDecimal volume = gateioTicker.getVolume(currencyPair.base.getCurrencyCode());

    return new Ticker.Builder().currencyPair(currencyPair).ask(ask).bid(bid).last(last).low(low).high(high).volume(volume).build();
  }

  public static LimitOrder adaptOrder(GateioPublicOrder order, CurrencyPair currencyPair, OrderType orderType) {

    return new LimitOrder(orderType, order.getAmount(), currencyPair, "", null, order.getPrice());
  }

  public static List<LimitOrder> adaptOrders(List<GateioPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (GateioPublicOrder bterOrder : orders) {
      limitOrders.add(adaptOrder(bterOrder, currencyPair, orderType));
    }

    return limitOrders;
  }

  public static OrderBook adaptOrderBook(GateioDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = GateioAdapters.adaptOrders(depth.getAsks(), currencyPair, OrderType.ASK);
    Collections.reverse(asks);
    List<LimitOrder> bids = GateioAdapters.adaptOrders(depth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public static LimitOrder adaptOrder(GateioOpenOrder order, Collection<CurrencyPair> currencyPairs) {

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

  public static OpenOrders adaptOpenOrders(GateioOpenOrders openOrders, Collection<CurrencyPair> currencyPairs) {

    List<LimitOrder> adaptedOrders = new ArrayList<>();
    for (GateioOpenOrder openOrder : openOrders.getOrders()) {
      adaptedOrders.add(adaptOrder(openOrder, currencyPairs));
    }

    return new OpenOrders(adaptedOrders);
  }

  public static OrderType adaptOrderType(GateioOrderType cryptoTradeOrderType) {

    return (cryptoTradeOrderType.equals(GateioOrderType.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static Trade adaptTrade(GateioTradeHistory.BTERPublicTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = adaptOrderType(trade.getType());
    Date timestamp = DateUtils.fromMillisUtc(trade.getDate() * 1000);

    return new Trade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getTradeId());
  }

  public static Trades adaptTrades(GateioTradeHistory tradeHistory, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();
    long lastTradeId = 0;
    for (GateioTradeHistory.BTERPublicTrade trade : tradeHistory.getTrades()) {
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

  public static Wallet adaptWallet(GateioFunds bterAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    for (Entry<String, BigDecimal> funds : bterAccountInfo.getAvailableFunds().entrySet()) {
      Currency currency = Currency.getInstance(funds.getKey().toUpperCase());
      BigDecimal amount = funds.getValue();
      BigDecimal locked = bterAccountInfo.getLockedFunds().get(currency.toString());

      balances.add(new Balance(currency, null, amount, locked == null ? BigDecimal.ZERO : locked));
    }

    return new Wallet(balances);
  }

  public static UserTrades adaptUserTrades(List<GateioTrade> userTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (GateioTrade userTrade : userTrades) {
      trades.add(adaptUserTrade(userTrade));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrade adaptUserTrade(GateioTrade gateioTrade) {

    OrderType orderType = adaptOrderType(gateioTrade.getType());
    Date timestamp = DateUtils.fromMillisUtc(gateioTrade.getTimeUnix() * 1000);
    CurrencyPair currencyPair = adaptCurrencyPair(gateioTrade.getPair());

    return new UserTrade(orderType, gateioTrade.getAmount(), currencyPair, gateioTrade.getRate(), timestamp, gateioTrade.getId(), null, null,
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
