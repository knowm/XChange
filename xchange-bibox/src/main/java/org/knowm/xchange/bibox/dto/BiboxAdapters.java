package org.knowm.xchange.bibox.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.bibox.dto.account.BiboxCoin;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxOrder;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBookEntry;
import org.knowm.xchange.bibox.dto.trade.BiboxOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrade.Builder;
import org.knowm.xchange.dto.trade.UserTrades;

/** @author odrotleff */
public class BiboxAdapters {

  public static String toBiboxPair(org.knowm.xchange.currency.CurrencyPair pair) {

    return pair.getBase().getCurrencyCode() + '_' + pair.getCounter().getCurrencyCode();
  }

  private static org.knowm.xchange.currency.CurrencyPair adaptCurrencyPair(String biboxPair) {
    String[] split = biboxPair.split("_");
    return org.knowm.xchange.currency.CurrencyPair.build(split[0], split[1]);
  }

  public static Ticker adaptTicker(
      BiboxTicker ticker, org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(ticker.getSell())
        .bid(ticker.getBuy())
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .last(ticker.getLast())
        .volume(ticker.getVol())
        .timestamp(new Date(ticker.getTimestamp()))
        .build();
  }

  public static AccountInfo adaptAccountInfo(List<BiboxCoin> coins) {
    Wallet wallet = adaptWallet(coins);
    return AccountInfo.build(wallet);
  }

  private static Wallet adaptWallet(List<BiboxCoin> coins) {
    List<Balance> balances =
        coins.stream().map(BiboxAdapters::adaptBalance).collect(Collectors.toList());
    return Wallet.build(balances);
  }

  private static org.knowm.xchange.dto.account.Balance adaptBalance(BiboxCoin coin) {
    return new org.knowm.xchange.dto.account.Balance.Builder()
        .setCurrency(Currency.valueOf(coin.getSymbol()))
        .setAvailable(coin.getBalance())
        .setFrozen(coin.getFreeze())
        .setTotal(coin.getTotalBalance())
        .createBalance();
  }

  public static OrderBook adaptOrderBook(
      BiboxOrderBook orderBook, org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return new OrderBook(
        new Date(orderBook.getUpdateTime()),
        orderBook
            .getAsks()
            .stream()
            .map(e -> adaptOrderBookOrder(e, OrderType.ASK, currencyPair))
            .collect(Collectors.toList()),
        orderBook
            .getBids()
            .stream()
            .map(e -> adaptOrderBookOrder(e, OrderType.BID, currencyPair))
            .collect(Collectors.toList()));
  }

  public static LimitOrder adaptOrderBookOrder(
      BiboxOrderBookEntry entry,
      OrderType orderType,
      org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .limitPrice(entry.getPrice())
        .originalAmount(entry.getVolume())
        .build();
  }

  public static OpenOrders adaptOpenOrders(BiboxOrders biboxOpenOrders) {
    return new OpenOrders(
        biboxOpenOrders
            .getItems()
            .stream()
            .map(BiboxAdapters::adaptLimitOpenOrder)
            .collect(Collectors.toList()));
  }

  private static LimitOrder adaptLimitOpenOrder(BiboxOrder biboxOrder) {
    org.knowm.xchange.currency.CurrencyPair currencyPair =
        org.knowm.xchange.currency.CurrencyPair.build(
            biboxOrder.getCoinSymbol(), biboxOrder.getCurrencySymbol());
    return new LimitOrder.Builder(biboxOrder.getOrderSide().getOrderType(), currencyPair)
        .id(String.valueOf(biboxOrder.getId()))
        .timestamp(new Date(biboxOrder.getCreatedAt()))
        .limitPrice(biboxOrder.getPrice())
        .originalAmount(biboxOrder.getAmount())
        .cumulativeAmount(biboxOrder.getDealAmount())
        .remainingAmount(biboxOrder.getUnexecuted())
        .orderStatus(biboxOrder.getStatus().getOrderStatus())
        .build();
  }

  public static ExchangeMetaData adaptMetadata(List<BiboxMarket> markets) {
    Map<org.knowm.xchange.currency.CurrencyPair, CurrencyPairMetaData> pairMeta = new HashMap<>();
    for (BiboxMarket biboxMarket : markets) {
      pairMeta.put(
          org.knowm.xchange.currency.CurrencyPair.build(
              biboxMarket.getCoinSymbol(), biboxMarket.getCurrencySymbol()),
          new CurrencyPairMetaData(null, null, null, null));
    }
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }

  public static UserTrades adaptUserTrades(BiboxOrders biboxOrderHistory) {
    List<UserTrade> trades =
        biboxOrderHistory
            .getItems()
            .stream()
            .map(BiboxAdapters::adaptUserTrade)
            .collect(Collectors.toList());
    return new UserTrades(trades, TradeSortType.SortByID);
  }

  private static UserTrade adaptUserTrade(BiboxOrder order) {
    return new Builder()
        .id(Long.toString(order.getId()))
        .currencyPair(
            org.knowm.xchange.currency.CurrencyPair.build(
                order.getCoinSymbol(), order.getCurrencySymbol()))
        .price(order.getPrice())
        .originalAmount(order.getAmount())
        .timestamp(new Date(order.getCreatedAt()))
        .type(order.getOrderSide().getOrderType())
        .feeCurrency(Currency.valueOf(order.getFeeSymbol()))
        .feeAmount(order.getFee())
        .build();
  }

  public static List<OrderBook> adaptAllOrderBooks(List<BiboxOrderBook> biboxOrderBooks) {
    return biboxOrderBooks
        .stream()
        .map(ob -> BiboxAdapters.adaptOrderBook(ob, adaptCurrencyPair(ob.getPair())))
        .collect(Collectors.toList());
  }
}
