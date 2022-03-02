package org.knowm.xchange.lykke;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.lykke.dto.account.LykkeWallet;
import org.knowm.xchange.lykke.dto.marketdata.LykkeOrderBook;
import org.knowm.xchange.lykke.dto.marketdata.LykkePrices;
import org.knowm.xchange.lykke.dto.trade.LykkeLimitOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeOrderType;
import org.knowm.xchange.utils.DateUtils;

public class LykkeAdapter {

  //    private static SimpleDateFormat formatter = new
  // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  public static String adaptToAssetPair(CurrencyPair currencyPair) {
    return currencyPair.toString().replace("/", "");
  }

  public static OrderBook adaptOrderBook(
      List<LykkeOrderBook> lykkeOrderBook, CurrencyPair currencyPair) throws IOException {
    List<LimitOrder> bidList = new ArrayList<>();
    List<LimitOrder> askList = new ArrayList<>();

    if (lykkeOrderBook.size() == 2) {
      for (LykkeOrderBook lykkeOrderBook1 : lykkeOrderBook) {
        if (lykkeOrderBook1.isBuy()) {
          bidList = adaptLimitOrders(lykkeOrderBook1, Order.OrderType.BID, currencyPair);
        } else {
          askList = adaptLimitOrders(lykkeOrderBook1, Order.OrderType.ASK, currencyPair);
        }
      }
    } else {
      throw new IndexOutOfBoundsException("Orderbook size is not 2.");
    }
    return new OrderBook(null, askList, bidList);
  }

  private static List<LimitOrder> adaptLimitOrders(
      LykkeOrderBook lykkeOrderBook, Order.OrderType orderType, CurrencyPair currencyPair)
      throws IOException {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (LykkePrices lykkePrices : lykkeOrderBook.getPrices()) {
      limitOrders.add(
          new LimitOrder(
              orderType,
              BigDecimal.valueOf(Math.abs(lykkePrices.getVolume()))
                  .setScale(8, RoundingMode.HALF_EVEN)
                  .stripTrailingZeros(),
              currencyPair,
              null,
              DateUtils.fromISO8601DateString(lykkeOrderBook.getTimestamp()),
              BigDecimal.valueOf(lykkePrices.getPrice())
                  .setScale(8, RoundingMode.HALF_EVEN)
                  .stripTrailingZeros()));
    }

    return limitOrders;
  }

  /** *****Adapter for LykkeOpenOrders***** */
  public static List<LimitOrder> adaptOpenOrders(
      List<CurrencyPair> currencyPairList, List<LykkeOrder> lykkeOrders) throws IOException {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (LykkeOrder lykkeOrder : lykkeOrders) {
      limitOrders.add(adaptLimitOrder(currencyPairList, lykkeOrder));
    }
    return limitOrders;
  }

  public static LimitOrder adaptLimitOrder(
      List<CurrencyPair> currencyPairList, LykkeOrder lykkeOrder) throws IOException {

    return new LimitOrder(
        getOrderTypeFromVolumeSign(lykkeOrder.getVolume()),
        BigDecimal.valueOf(Math.abs(lykkeOrder.getVolume()))
            .setScale(8, RoundingMode.HALF_EVEN)
            .stripTrailingZeros(),
        adaptToCurrencyPair(currencyPairList, lykkeOrder.getAssetPairId()),
        lykkeOrder.getId(),
        DateUtils.fromISO8601DateString(
            lykkeOrder.getCreatedAt()), // formatter.parse(lykkeOrder.getCreatedAt())
        BigDecimal.valueOf(lykkeOrder.getPrice())
            .setScale(8, RoundingMode.HALF_EVEN)
            .stripTrailingZeros());
  }

  /** **Adapter for LykkeTradeHistory*** */
  public static List<UserTrade> adaptUserTrades(
      List<CurrencyPair> currencyPairList, List<LykkeOrder> tradeHistoryList) throws IOException {
    List<UserTrade> userTrades = new ArrayList<>();

    for (LykkeOrder lykkeTradeHistory : tradeHistoryList) {
      userTrades.add(adaptUserTrade(currencyPairList, lykkeTradeHistory));
    }
    return userTrades;
  }

  private static UserTrade adaptUserTrade(
      List<CurrencyPair> currencyPairList, LykkeOrder tradeHistory) throws IOException {

    return new UserTrade.Builder()
        .type(getOrderTypeFromVolumeSign(tradeHistory.getVolume()))
        .originalAmount(
            BigDecimal.valueOf(Math.abs(tradeHistory.getVolume()))
                .setScale(8, RoundingMode.HALF_EVEN)
                .stripTrailingZeros())
        .currencyPair(adaptToCurrencyPair(currencyPairList, tradeHistory.getAssetPairId()))
        .price(
            BigDecimal.valueOf(tradeHistory.getPrice())
                .setScale(8, RoundingMode.HALF_EVEN)
                .stripTrailingZeros())
        .timestamp(DateUtils.fromISO8601DateString(tradeHistory.getCreatedAt()))
        .id(tradeHistory.getId()) // .substring(tradeHistory.getId().lastIndexOf('_')+1
        .orderId(tradeHistory.getId())
        .build();
  }

  public static LykkeLimitOrder adaptLykkeOrder(LimitOrder limitOrder) {
    return new LykkeLimitOrder(
        adaptToAssetPair(limitOrder.getCurrencyPair()),
        LykkeOrderType.fromOrderType(limitOrder.getType()),
        limitOrder.getOriginalAmount().doubleValue(),
        limitOrder.getLimitPrice().doubleValue());
  }

  public static AccountInfo adaptAccountInfo(List<LykkeWallet> lykkeWallets) {
    List<Balance> balances = new ArrayList<>();
    for (LykkeWallet lykkeWallet : lykkeWallets) {
      balances.add(
          new Balance(
              new Currency(lykkeWallet.getAssetId()),
              BigDecimal.valueOf(lykkeWallet.getBalance())
                  .setScale(8, RoundingMode.HALF_EVEN)
                  .stripTrailingZeros(),
              BigDecimal.valueOf(lykkeWallet.getBalance() - lykkeWallet.getReserved())
                  .setScale(8, RoundingMode.HALF_EVEN)
                  .stripTrailingZeros(),
              BigDecimal.valueOf(lykkeWallet.getReserved())
                  .setScale(8, RoundingMode.HALF_EVEN)
                  .stripTrailingZeros()));
    }
    return new AccountInfo(Wallet.Builder.from(balances).id("apiWallet").build());
  }

  private static Order.OrderType getOrderTypeFromVolumeSign(double volume) {
    // if volume has minus sign, means that orderType is SELL(ASK)
    if (volume < 0) {
      return Order.OrderType.ASK;
    }
    return Order.OrderType.BID;
  }

  private static CurrencyPair adaptToCurrencyPair(
      List<CurrencyPair> currencyPairList, String assetPair) throws IOException {
    for (CurrencyPair currencyPair : currencyPairList) {
      if (currencyPair.toString().replace("/", "").equals(assetPair)) {
        return currencyPair;
      }
    }
    throw new IOException("This assetPair doesn't exist in Lykke exchange.");
  }

  private static Date extractTimestampInput(String strDate) {
    final List<String> dateFormats =
        Arrays.asList("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'");

    for (String format : dateFormats) {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      try {
        return sdf.parse(strDate);
      } catch (ParseException e) {
        // intentionally empty
      }
    }
    throw new IllegalArgumentException(
        "Invalid input for date. Given '"
            + strDate
            + "', expecting format yyyy-MM-dd'T'HH:mm:ss.SSS'Z' or yyyy-MM-dd'T'HH:mm:ss.SS'Z'.");
  }
}
