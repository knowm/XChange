package org.knowm.xchange.okcoin;

import static org.knowm.xchange.currency.Currency.BCH;
import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.LTC;
import static org.knowm.xchange.currency.Currency.USD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;
import org.knowm.xchange.okcoin.dto.account.OkCoinFunds;
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesInfoCross;
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesUserInfoCross;
import org.knowm.xchange.okcoin.dto.account.OkCoinRecords;
import org.knowm.xchange.okcoin.dto.account.OkCoinRecords.RechargeStatus;
import org.knowm.xchange.okcoin.dto.account.OkCoinRecords.WithdrawalStatus;
import org.knowm.xchange.okcoin.dto.account.OkCoinUserInfo;
import org.knowm.xchange.okcoin.dto.account.OkcoinFuturesFundsCross;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTrade;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrder;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesTradeHistoryResult;
import org.knowm.xchange.okcoin.dto.trade.OkCoinFuturesTradeHistoryResult.TransactionType;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrder;
import org.knowm.xchange.okcoin.dto.trade.OkCoinOrderResult;
import org.knowm.xchange.utils.DateUtils;

public final class OkCoinAdapters {

  private static final Balance zeroUsdBalance =
      new Builder().setCurrency(USD).setTotal(BigDecimal.ZERO).createBalance();

  private OkCoinAdapters() {}

  public static String adaptSymbol(CurrencyPair currencyPair) {

    return (currencyPair.getBase().getCurrencyCode()
            + '_'
            + currencyPair.getCounter().getCurrencyCode())
        .toLowerCase();
  }

  public static String adaptSymbol(Currency currency) {

    return currency.getCurrencyCode().toLowerCase();
  }

  public static CurrencyPair adaptSymbol(String symbol) {

    String[] currencies = symbol.toUpperCase().split("_");
    return CurrencyPair.build(currencies[0], currencies[1]);
  }

  public static Ticker adaptTicker(OkCoinTickerResponse tickerResponse, CurrencyPair currencyPair) {
    final Date date = adaptDate(tickerResponse.getDate());
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .high(tickerResponse.getTicker().getHigh())
        .low(tickerResponse.getTicker().getLow())
        .bid(tickerResponse.getTicker().getBuy())
        .ask(tickerResponse.getTicker().getSell())
        .last(tickerResponse.getTicker().getLast())
        .volume(tickerResponse.getTicker().getVol())
        .timestamp(date)
        .build();
  }

  public static OrderBook adaptOrderBook(OkCoinDepth depth, CurrencyPair currencyPair) {
    Stream<LimitOrder> asks =
        adaptLimitOrders(OrderType.ASK, depth.getAsks(), depth.getTimestamp(), currencyPair)
            .sorted();
    Stream<LimitOrder> bids =
        adaptLimitOrders(OrderType.BID, depth.getBids(), depth.getTimestamp(), currencyPair)
            .sorted();
    return new OrderBook(depth.getTimestamp(), asks, bids);
  }

  public static Trades adaptTrades(OkCoinTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.length);
    for (OkCoinTrade trade : trades) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    long lastTid = trades.length > 0 ? (trades[trades.length - 1].getTid()) : 0L;
    return new Trades(tradeList, lastTid, TradeSortType.SortByTimestamp);
  }

  public static AccountInfo adaptAccountInfo(OkCoinUserInfo userInfo) {

    OkCoinFunds funds = userInfo.getInfo().getFunds();

    Map<String, Builder> builders = new TreeMap<>();

    for (Entry<String, BigDecimal> available : funds.getFree().entrySet()) {
      builders.put(
          available.getKey(),
          new Builder()
              .setCurrency(Currency.valueOf(available.getKey()))
              .setAvailable(available.getValue()));
    }

    for (Entry<String, BigDecimal> frozen : funds.getFreezed().entrySet()) {
      Builder builder = builders.get(frozen.getKey());
      if (builder == null) {
        builder = new Builder().setCurrency(Currency.valueOf(frozen.getKey()));
      }
      builders.put(frozen.getKey(), builder.setFrozen(frozen.getValue()));
    }

    for (Entry<String, BigDecimal> borrowed : funds.getBorrow().entrySet()) {
      Builder builder = builders.get(borrowed.getKey());
      if (builder == null) {
        builder = new Builder().setCurrency(Currency.valueOf(borrowed.getKey()));
      }
      builders.put(borrowed.getKey(), builder.setBorrowed(borrowed.getValue()));
    }

    List<Balance> wallet = new ArrayList<>(builders.size());

    for (Builder builder : builders.values()) {
      wallet.add(builder.createBalance());
    }

    return AccountInfo.build(Wallet.build(wallet));
  }

  public static AccountInfo adaptAccountInfoFutures(OkCoinFuturesUserInfoCross futureUserInfo) {
    OkCoinFuturesInfoCross info = futureUserInfo.getInfo();
    OkcoinFuturesFundsCross btcFunds = info.getBtcFunds();
    OkcoinFuturesFundsCross ltcFunds = info.getLtcFunds();
    OkcoinFuturesFundsCross bchFunds = info.getBchFunds();

    Balance btcBalance =
        new Builder().setCurrency(BTC).setTotal(btcFunds.getAccountRights()).createBalance();
    Balance ltcBalance =
        new Builder().setCurrency(LTC).setTotal(ltcFunds.getAccountRights()).createBalance();
    Balance bchBalance =
        new Builder().setCurrency(BCH).setTotal(bchFunds.getAccountRights()).createBalance();

    return AccountInfo.build(Wallet.build(zeroUsdBalance, btcBalance, ltcBalance, bchBalance));
  }

  public static OpenOrders adaptOpenOrders(List<OkCoinOrderResult> orderResults) {
    List<LimitOrder> openOrders = new ArrayList<>();

    for (OkCoinOrderResult orderResult : orderResults) {
      OkCoinOrder[] orders = orderResult.getOrders();
      for (OkCoinOrder singleOrder : orders) {
        openOrders.add(adaptOpenOrder(singleOrder));
      }
    }
    return new OpenOrders(openOrders);
  }

  public static OpenOrders adaptOpenOrdersFutures(List<OkCoinFuturesOrderResult> orderResults) {
    List<LimitOrder> openOrders = new ArrayList<>();

    for (OkCoinFuturesOrderResult orderResult : orderResults) {
      OkCoinFuturesOrder[] orders = orderResult.getOrders();
      for (OkCoinFuturesOrder singleOrder : orders) {
        openOrders.add(adaptOpenOrderFutures(singleOrder));
      }
    }
    return new OpenOrders(openOrders);
  }

  public static UserTrades adaptTrades(OkCoinOrderResult orderResult) {

    List<UserTrade> trades = new ArrayList<>(orderResult.getOrders().length);
    for (int i = 0; i < orderResult.getOrders().length; i++) {
      OkCoinOrder order = orderResult.getOrders()[i];

      // skip cancels that have not yet been filtered out
      if (order.getDealAmount().equals(BigDecimal.ZERO)) {
        continue;
      }
      trades.add(adaptTrade(order));
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static UserTrades adaptTradesFutures(OkCoinFuturesOrderResult orderResult) {

    List<UserTrade> trades = new ArrayList<>(orderResult.getOrders().length);
    for (int i = 0; i < orderResult.getOrders().length; i++) {
      OkCoinFuturesOrder order = orderResult.getOrders()[i];

      // skip cancels that have not yet been filtered out
      if (order.getDealAmount().equals(BigDecimal.ZERO)) {
        continue;
      }
      trades.add(adaptTradeFutures(order));
    }
    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  private static Stream<LimitOrder> adaptLimitOrders(
      OrderType type, BigDecimal[][] list, Date timestamp, CurrencyPair currencyPair) {
    return Arrays.stream(list)
        .map(data -> adaptLimitOrder(type, data, currencyPair, null, timestamp));
  }

  private static LimitOrder adaptLimitOrder(
      OrderType type, BigDecimal[] data, CurrencyPair currencyPair, String id, Date timestamp) {

    return new LimitOrder(type, data[1], currencyPair, id, timestamp, data[0]);
  }

  private static Trade adaptTrade(OkCoinTrade trade, CurrencyPair currencyPair) {

    return new Trade(
        trade.getType().equals("buy") ? OrderType.BID : OrderType.ASK,
        trade.getAmount(),
        currencyPair,
        trade.getPrice(),
        trade.getDate(),
        String.valueOf(trade.getTid()));
  }

  private static LimitOrder adaptOpenOrder(OkCoinOrder order) {

    return new LimitOrder(
        adaptOrderType(order.getType()),
        order.getAmount(),
        adaptSymbol(order.getSymbol()),
        String.valueOf(order.getOrderId()),
        order.getCreateDate(),
        order.getPrice(),
        order.getAveragePrice(),
        order.getDealAmount(),
        null,
        adaptOrderStatus(order.getStatus()));
  }

  public static LimitOrder adaptOpenOrderFutures(OkCoinFuturesOrder order) {
    return new LimitOrder(
        adaptOrderType(order.getType()),
        order.getAmount(),
        adaptSymbol(order.getSymbol()),
        String.valueOf(order.getOrderId()),
        order.getCreatedDate(),
        order.getPrice(),
        order.getAvgPrice(),
        order.getDealAmount(),
        order.getFee(),
        adaptOrderStatus(order.getStatus()));
  }

  public static OrderType adaptOrderType(String type) {

    switch (type) {
      case "buy":
        return OrderType.BID;
      case "buy_market":
        return OrderType.BID;
      case "sell":
        return OrderType.ASK;
      case "sell_market":
        return OrderType.ASK;
      case "1":
        return OrderType.BID;
      case "2":
        return OrderType.ASK;
      case "3":
        return OrderType.EXIT_ASK;
      case "4":
        return OrderType.EXIT_BID;
      default:
        return null;
    }
  }

  public static OrderStatus adaptOrderStatus(int status) {
    switch (status) {
      case -1:
        return OrderStatus.CANCELED;
      case 0:
        return OrderStatus.NEW;
      case 1:
        return OrderStatus.PARTIALLY_FILLED;
      case 2:
        return OrderStatus.FILLED;
      case 4:
        return OrderStatus.PENDING_CANCEL;
      default:
        return null;
    }
  }

  private static UserTrade adaptTrade(OkCoinOrder order) {

    // Order fill status is being adapted to a trade, there is no dedicated tradeId, so user orderId
    // instead.
    String tradeId, orderId;
    tradeId = orderId = String.valueOf(order.getOrderId());
    return new UserTrade(
        adaptOrderType(order.getType()),
        order.getDealAmount(),
        adaptSymbol(order.getSymbol()),
        order.getAveragePrice(),
        order.getCreateDate(),
        tradeId,
        orderId,
        null,
        null);
  }

  private static UserTrade adaptTradeFutures(OkCoinFuturesOrder order) {

    return new UserTrade(
        adaptOrderType(order.getType()),
        order.getDealAmount(),
        adaptSymbol(order.getSymbol()),
        order.getPrice(),
        order.getCreatedDate(),
        null,
        String.valueOf(order.getOrderId()),
        null,
        (Currency) null);
  }

  public static UserTrades adaptTradeHistory(
      OkCoinFuturesTradeHistoryResult[] okCoinFuturesTradeHistoryResult) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (OkCoinFuturesTradeHistoryResult okCoinFuturesTrade : okCoinFuturesTradeHistoryResult) {
      //  if (okCoinFuturesTrade.getType().equals(OkCoinFuturesTradeHistoryResult.TransactionType.))
      // { // skip account deposits and withdrawals.
      OrderType orderType =
          okCoinFuturesTrade.getType().equals(TransactionType.sell) ? OrderType.ASK : OrderType.BID;
      BigDecimal originalAmount = BigDecimal.valueOf(okCoinFuturesTrade.getAmount());
      BigDecimal price = okCoinFuturesTrade.getPrice();
      Date timestamp = new Date(okCoinFuturesTrade.getTimestamp());
      long transactionId = okCoinFuturesTrade.getId();
      if (transactionId > lastTradeId) {
        lastTradeId = transactionId;
      }
      final String tradeId = String.valueOf(transactionId);
      final String orderId = String.valueOf(okCoinFuturesTrade.getId());
      final CurrencyPair currencyPair = CurrencyPair.BTC_USD;

      BigDecimal feeAmont = BigDecimal.ZERO;
      UserTrade trade =
          new UserTrade(
              orderType,
              originalAmount,
              currencyPair,
              price,
              timestamp,
              tradeId,
              orderId,
              feeAmont,
              Currency.valueOf(currencyPair.getCounter().getCurrencyCode()));
      trades.add(trade);
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  private static Date adaptDate(long date) {
    return DateUtils.fromMillisUtc(date);
  }

  public static List<FundingRecord> adaptFundingHistory(
      final OkCoinAccountRecords[] okCoinAccountRecordsList) {
    final List<FundingRecord> fundingRecords = new ArrayList<>();
    if (okCoinAccountRecordsList != null && okCoinAccountRecordsList.length > 0) {
      final OkCoinAccountRecords depositRecord = okCoinAccountRecordsList[0];
      if (depositRecord != null) {
        final Currency depositCurrency = Currency.valueOf(depositRecord.getSymbol());
        for (OkCoinRecords okCoinRecordEntry : depositRecord.getRecords()) {

          Status status = null;
          if (okCoinRecordEntry.getStatus() != null) {
            final RechargeStatus rechargeStatus =
                RechargeStatus.fromInt(okCoinRecordEntry.getStatus());
            if (rechargeStatus != null) {
              status = Status.resolveStatus(rechargeStatus.getStatus());
            }
          }

          fundingRecords.add(
              new FundingRecord(
                  okCoinRecordEntry.getAddress(),
                  adaptDate(okCoinRecordEntry.getDate()),
                  depositCurrency,
                  okCoinRecordEntry.getAmount(),
                  null,
                  null,
                  Type.DEPOSIT,
                  status,
                  null,
                  okCoinRecordEntry.getFee(),
                  null));
        }
      }
      final OkCoinAccountRecords withdrawalRecord = okCoinAccountRecordsList[1];
      if (withdrawalRecord != null) {
        final Currency withdrawalCurrency = Currency.valueOf(withdrawalRecord.getSymbol());
        for (OkCoinRecords okCoinRecordEntry : withdrawalRecord.getRecords()) {

          Status status = null;
          if (okCoinRecordEntry.getStatus() != null) {
            final WithdrawalStatus withdrawalStatus =
                WithdrawalStatus.fromInt(okCoinRecordEntry.getStatus());
            if (withdrawalStatus != null) {
              status = Status.resolveStatus(withdrawalStatus.getStatus());
            }
          }

          fundingRecords.add(
              new FundingRecord(
                  okCoinRecordEntry.getAddress(),
                  adaptDate(okCoinRecordEntry.getDate()),
                  withdrawalCurrency,
                  okCoinRecordEntry.getAmount(),
                  null,
                  null,
                  Type.WITHDRAWAL,
                  status,
                  null,
                  okCoinRecordEntry.getFee(),
                  null));
        }
      }
    }
    return fundingRecords;
  }
}
