package org.known.xchange.dsx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.utils.DateUtils;
import org.known.xchange.dsx.dto.account.DSXAccountInfo;
import org.known.xchange.dsx.dto.marketdata.DSXTicker;
import org.known.xchange.dsx.dto.marketdata.DSXTrade;
import org.known.xchange.dsx.dto.trade.DSXOrder;

/**
 * @author Mikhail Wall
 */

public class DSXAdapters {

  private DSXAdapters() {

  }

  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> dSXOrders, CurrencyPair currencyPair, String orderTypeString, String id) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    for (BigDecimal[] dsxOrder : dSXOrders) {
      limitOrders.add(adaptOrders(dsxOrder[1], dsxOrder[0], currencyPair, orderType, id));
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrders(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, String id) {

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  public static Trade adaptTrade(DSXTrade dSXTrade, CurrencyPair currencyPair) {

    OrderType orderType = dSXTrade.getTradeType().equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = dSXTrade.getAmount();
    BigDecimal price = dSXTrade.getPrice();
    Date date = DateUtils.fromMillisUtc(dSXTrade.getDate() * 1000L);

    final String tradeId = String.valueOf(dSXTrade.getTid());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(DSXTrade[] dSXTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>();
    long lastTradeId = 0;
    for (DSXTrade dSXTrade : dSXTrades) {

      long tradeId = dSXTrade.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(0, adaptTrade(dSXTrade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, Trades.TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(DSXTicker dSXTicker, CurrencyPair currencyPair) {

    BigDecimal last = dSXTicker.getLast();
    BigDecimal bid = dSXTicker.getSell();
    BigDecimal ask = dSXTicker.getBuy();
    BigDecimal high = dSXTicker.getHigh();
    BigDecimal low = dSXTicker.getAvg();
    BigDecimal avg = dSXTicker.getVolCur();
    BigDecimal volume = dSXTicker.getVolCur();
    Date timestamp = DateUtils.fromMillisUtc(dSXTicker.getUpdated() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).vwap(avg).volume(volume)
        .timestamp(timestamp).build();
  }

  public static Wallet adaptWallet(DSXAccountInfo dsxAccountInfo) {

    List<Balance> balances = new ArrayList<>();
    Map<String, BigDecimal> funds = dsxAccountInfo.getFunds();

    for (String lcCurrency : funds.keySet()) {
      BigDecimal fund = funds.get(lcCurrency);
      Currency currency = Currency.getInstance(lcCurrency);
      balances.add(new Balance(currency, fund));
    }
    return new Wallet(balances);
  }

  public static OpenOrders adaptOrders(Map<Long, DSXOrder> dsxOrderMap) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (Long id : dsxOrderMap.keySet()) {
      DSXOrder dsxOrder = dsxOrderMap.get(id);
      OrderType orderType = dsxOrder.getType() == DSXOrder.Type.buy ? OrderType.BID : OrderType.ASK;
      BigDecimal price = dsxOrder.getRate();
      Date timestamp = DateUtils.fromMillisUtc(dsxOrder.getTimestampCreated() * 1000L);
      CurrencyPair currencyPair = adaptCurrencyPair(dsxOrder.getPair());

      limitOrders.add(new LimitOrder(orderType, dsxOrder.getAmount(), currencyPair, Long.toString(id), timestamp, price));
    }
    return new OpenOrders(limitOrders);
  }

  public static CurrencyPair adaptCurrencyPair(String dsxCurrencyPair) {

    String[] currencies = dsxCurrencyPair.split("_");
    return new CurrencyPair(currencies[0].toUpperCase(), currencies[1].toUpperCase());
  }

  public static List<CurrencyPair> adaptCurrencyPair(Iterable<String> dsxPairs) {

    List<CurrencyPair> pairs = new ArrayList<>();
    for (String dsxPair : dsxPairs) {
      pairs.add(adaptCurrencyPair(dsxPair));
    }

    return pairs;
  }
}
