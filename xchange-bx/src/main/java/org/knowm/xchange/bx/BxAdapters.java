package org.knowm.xchange.bx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.knowm.xchange.bx.dto.account.BxBalance;
import org.knowm.xchange.bx.dto.marketdata.BxAssetPair;
import org.knowm.xchange.bx.dto.marketdata.BxTicker;
import org.knowm.xchange.bx.dto.trade.BxOrder;
import org.knowm.xchange.bx.dto.trade.BxTradeHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class BxAdapters {

  private static final String BUY = "buy";
  private static final String SELL = "sell";
  private static final String TRADE = "trade";
  private static final String FEE = "fee";

  public static ExchangeMetaData adaptToExchangeMetaData(Map<String, BxAssetPair> assetPairs) {
    BxUtils.setBxAssetPairs(assetPairs);
    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    for (String id : assetPairs.keySet()) {
      if (assetPairs.get(id).isActive()) {
        pairs.put(adaptCurrencyPair(id), adaptCurrencyPairMetaData(assetPairs.get(id)));
      }
    }
    return new ExchangeMetaData(pairs, BxUtils.getCurrencies(), null, null, false);
  }

  private static CurrencyPair adaptCurrencyPair(String pairId) {
    return BxUtils.translateBxCurrencyPair(pairId);
  }

  private static CurrencyPairMetaData adaptCurrencyPairMetaData(BxAssetPair assetPair) {
    return new CurrencyPairMetaData(null, assetPair.getPrimaryMin(), null, 0, null);
  }

  public static Ticker adaptTicker(BxTicker bxTicker, SynchronizedValueFactory<Long> nonce) {
    Ticker.Builder builder = new Ticker.Builder();
    builder.currencyPair(BxUtils.translateBxCurrencyPair(bxTicker.getPairingId()));
    builder.last(bxTicker.getLastPrice());
    builder.bid(bxTicker.getOrderBook().getBids().getHighBid());
    builder.ask(bxTicker.getOrderBook().getAsks().getHighBid());
    builder.volume(bxTicker.getVolume24hours());
    builder.timestamp(new Date(nonce.createValue()));
    return builder.build();
  }

  public static String adaptOrderType(OrderType orderType) {
    if (orderType == OrderType.BID) {
      return BUY;
    } else if (orderType == OrderType.ASK) {
      return SELL;
    } else {
      throw new ExchangeException("Unsupported order type");
    }
  }

  private static OrderType adaptBxOrderType(String orderType) {
    OrderType result = null;
    if (orderType.equals(BUY)) {
      result = OrderType.BID;
    } else if (orderType.equals(SELL)) {
      result = OrderType.ASK;
    }
    return result;
  }

  public static OpenOrders adaptOpenOrders(BxOrder[] orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BxOrder order : orders) {
      limitOrders.add((LimitOrder) adaptOrder(order));
    }
    return new OpenOrders(limitOrders);
  }

  private static Date adaptDate(String date) {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date result;
    try {
      result = format.parse(date);
    } catch (ParseException e) {
      throw new ExchangeException("Date/time parse error: " + e.getMessage());
    }
    return result;
  }

  public static Order adaptOrder(BxOrder bxOrder) {
    return new LimitOrder(
        adaptBxOrderType(bxOrder.getOrderType()),
        bxOrder.getAmount(),
        BxUtils.translateBxCurrencyPair(bxOrder.getPairingId()),
        bxOrder.getOrderId(),
        adaptDate(bxOrder.getDate()),
        bxOrder.getRate());
  }

  public static Wallet adaptWallet(Map<String, BxBalance> currencies) {
    List<Balance> balances = new ArrayList<>();
    for (String record : currencies.keySet()) {
      Balance balance =
          new Balance(
              adaptCurrency(record),
              currencies.get(record).getTotal(),
              currencies.get(record).getAvailable(),
              currencies.get(record).getOrders(),
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              currencies.get(record).getWithdrawals(),
              currencies.get(record).getDeposits());
      balances.add(balance);
    }
    return Wallet.Builder.from(balances).build();
  }

  private static Currency adaptCurrency(String currency) {
    return BxUtils.translateCurrency(currency);
  }

  public static UserTrades adaptUserTrades(Map<String, List<BxTradeHistory>> historyMap) {
    List<UserTrade> trades = new ArrayList<>();
    for (String key : historyMap.keySet()) {
      UserTrade trade = adaptUserTrade(historyMap.get(key));
      if (trade != null) {
        trades.add(trade);
      }
    }
    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  private static UserTrade adaptUserTrade(List<BxTradeHistory> histories) {
    UserTrade trade = null;
    int indexOfFirstTrade = -1;
    int indexOfSecondTrade = -1;
    int indexOfFee = -1;
    for (int i = 0; i < histories.size(); i++) {
      if (histories.get(i).getType().equals(TRADE)) {
        if (indexOfFirstTrade < 0) {
          indexOfFirstTrade = i;
        } else if (indexOfSecondTrade < 0) {
          indexOfSecondTrade = i;
        }
      } else if ((histories.get(i).getType().equals(FEE)) && (indexOfFee < 0)) {
        indexOfFee = i;
      }
    }
    if ((indexOfFirstTrade > -1) && (indexOfSecondTrade > -1)) {
      if (!histories.get(indexOfSecondTrade).getAmount().equals(BigDecimal.ZERO)) {
        BxTradeHistory history = histories.get(indexOfSecondTrade);
        trade =
            new UserTrade.Builder()
                .type(
                    (history.getAmount().compareTo(BigDecimal.ZERO) > 0)
                        ? OrderType.BID
                        : OrderType.ASK)
                .originalAmount(history.getAmount().abs())
                .currencyPair(
                    new CurrencyPair(
                        history.getCurrency(), histories.get(indexOfFirstTrade).getCurrency()))
                .price(
                    histories
                        .get(indexOfFirstTrade)
                        .getAmount()
                        .divide(history.getAmount(), 6, RoundingMode.UP)
                        .abs())
                .timestamp(adaptDate(history.getDate()))
                .id(String.valueOf(history.getTransactionId()))
                .orderId(String.valueOf(history.getRefId()))
                .feeAmount((indexOfFee < 0) ? null : histories.get(indexOfFee).getAmount().abs())
                .feeCurrency(
                    (indexOfFee < 0)
                        ? null
                        : BxUtils.translateCurrency(histories.get(indexOfFee).getCurrency()))
                .build();
      }
    }
    return trade;
  }
}
