package org.knowm.xchange.bibox.dto;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.bibox.dto.account.BiboxAsset;
import org.knowm.xchange.bibox.dto.account.BiboxDeposit;
import org.knowm.xchange.bibox.dto.account.BiboxWithdrawal;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxDeals;
import org.knowm.xchange.bibox.dto.trade.BiboxOrder;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBookEntry;
import org.knowm.xchange.bibox.dto.trade.BiboxOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
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

/** @author odrotleff */
public class BiboxAdapters {

  public static String toBiboxPair(CurrencyPair pair) {

    return pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode();
  }

  private static CurrencyPair adaptCurrencyPair(String biboxPair) {
    String[] split = biboxPair.split("_");
    return new CurrencyPair(split[0], split[1]);
  }

  public static Ticker adaptTicker(BiboxTicker ticker, CurrencyPair currencyPair) {
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

  public static AccountInfo adaptAccountInfo(List<BiboxAsset> coins) {
    Wallet wallet = adaptWallet(coins);
    return new AccountInfo(wallet);
  }

  private static Wallet adaptWallet(List<BiboxAsset> coins) {
    List<Balance> balances =
        coins.stream().map(BiboxAdapters::adaptBalance).collect(Collectors.toList());
    return Wallet.Builder.from(balances).build();
  }

  private static Balance adaptBalance(BiboxAsset asset) {
    return new Balance.Builder()
        .currency(asset.getCoin_symbol())
        .available(asset.getBalance())
        .frozen(asset.getFreeze())
        .total(asset.getBalance().add(asset.getFreeze()))
        .build();
  }

  public static OrderBook adaptOrderBook(BiboxOrderBook orderBook, CurrencyPair currencyPair) {
    return new OrderBook(
        new Date(orderBook.getUpdateTime()),
        orderBook.getAsks().stream()
            .map(e -> adaptOrderBookOrder(e, OrderType.ASK, currencyPair))
            .collect(Collectors.toList()),
        orderBook.getBids().stream()
            .map(e -> adaptOrderBookOrder(e, OrderType.BID, currencyPair))
            .collect(Collectors.toList()));
  }

  public static LimitOrder adaptOrderBookOrder(
      BiboxOrderBookEntry entry, OrderType orderType, CurrencyPair currencyPair) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .limitPrice(entry.getPrice())
        .originalAmount(entry.getVolume())
        .build();
  }

  public static OpenOrders adaptOpenOrders(BiboxOrders biboxOpenOrders) {
    return new OpenOrders(
        biboxOpenOrders.getItems().stream()
            .map(BiboxAdapters::adaptLimitOpenOrder)
            .collect(Collectors.toList()));
  }

  private static LimitOrder adaptLimitOpenOrder(BiboxOrder biboxOrder) {
    CurrencyPair currencyPair =
        new CurrencyPair(biboxOrder.getCoinSymbol(), biboxOrder.getCurrencySymbol());
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
    Map<CurrencyPair, CurrencyPairMetaData> pairMeta = new HashMap<>();
    for (BiboxMarket biboxMarket : markets) {
      pairMeta.put(
          new CurrencyPair(biboxMarket.getCoinSymbol(), biboxMarket.getCurrencySymbol()),
          new CurrencyPairMetaData(null, null, null, null, null));
    }
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }

  public static UserTrades adaptUserTrades(BiboxOrders biboxOrderHistory) {
    List<UserTrade> trades =
        biboxOrderHistory.getItems().stream()
            .map(BiboxAdapters::adaptUserTrade)
            .collect(Collectors.toList());
    return new UserTrades(trades, TradeSortType.SortByID);
  }

  private static UserTrade adaptUserTrade(BiboxOrder order) {
    return new UserTrade.Builder()
        .orderId(order.getId())
        .id(order.getId())
        .currencyPair(new CurrencyPair(order.getCoinSymbol(), order.getCurrencySymbol()))
        .price(order.getPrice())
        .originalAmount(order.getAmount())
        .timestamp(new Date(order.getCreatedAt()))
        .type(order.getOrderSide().getOrderType())
        .feeCurrency(Currency.getInstance(order.getFeeSymbol()))
        .feeAmount(order.getFee())
        .build();
  }

  public static List<OrderBook> adaptAllOrderBooks(List<BiboxOrderBook> biboxOrderBooks) {
    return biboxOrderBooks.stream()
        .map(ob -> BiboxAdapters.adaptOrderBook(ob, adaptCurrencyPair(ob.getPair())))
        .collect(Collectors.toList());
  }

  public static Date convert(String s) {
    try {
      return DateUtils.fromISODateString(s);
    } catch (InvalidFormatException e) {
      throw new RuntimeException("Could not parse date: " + s, e);
    }
  }

  public static FundingRecord adaptDeposit(BiboxDeposit d) {
    return new FundingRecord(
        d.to,
        d.getCreatedAt(),
        Currency.getInstance(d.coinSymbol),
        d.amount,
        null,
        null,
        Type.DEPOSIT,
        convertStatus(d.status),
        null,
        null,
        null);
  }

  public static FundingRecord adaptDeposit(BiboxWithdrawal w) {
    return new FundingRecord(
        w.toAddress,
        w.getCreatedAt(),
        Currency.getInstance(w.coinSymbol),
        w.amountReal,
        null,
        null,
        Type.WITHDRAWAL,
        convertStatus(w.status),
        null,
        null,
        null);
  }

  public static Status convertStatus(int status) {
    switch (status) {
      case 1:
        return Status.PROCESSING;
      case 2:
        return Status.COMPLETE;
      case 3:
        return Status.FAILED;
      default:
        throw new RuntimeException("Unknown status of bibox deposit: " + status);
    }
  }

  public static Trades adaptDeals(List<BiboxDeals> biboxDeals, CurrencyPair currencyPair) {
    List<Trade> trades =
        biboxDeals.stream()
            .map(
                d ->
                    new Trade.Builder()
                        .type(convertSide(d.getSide()))
                        .originalAmount(d.getAmount())
                        .currencyPair(currencyPair)
                        .price(d.getPrice())
                        .timestamp(new Date(d.getTime()))
                        .id(d.getId())
                        .build())
            .collect(Collectors.toList());
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }
  /**
   * transaction side，1-bid，2-ask
   *
   * @param side
   * @return
   */
  private static OrderType convertSide(int side) {
    switch (side) {
      case 1:
        return OrderType.BID;
      case 2:
        return OrderType.ASK;
      default:
        throw new RuntimeException("Unknown order type (side) of bibox deal: " + side);
    }
  }
}
