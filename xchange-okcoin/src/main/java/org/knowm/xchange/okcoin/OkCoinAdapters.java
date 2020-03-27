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
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
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
import org.knowm.xchange.okcoin.dto.account.OkCoinFuturesUserInfoCross;
import org.knowm.xchange.okcoin.dto.account.OkCoinRecords;
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

  private static final Balance zeroUsdBalance = new Balance(USD, BigDecimal.ZERO);

  private OkCoinAdapters() {}

  public static String adaptSymbol(CurrencyPair currencyPair) {

    return (currencyPair.base.getCurrencyCode() + "_" + currencyPair.counter.getCurrencyCode())
        .toLowerCase();
  }

  public static String adaptSymbol(Currency currency) {

    return currency.getCurrencyCode().toLowerCase();
  }

  public static CurrencyPair adaptSymbol(String symbol) {

    String[] currencies = symbol.toUpperCase().split("_");
    return new CurrencyPair(currencies[0], currencies[1]);
  }

  public static String adaptCurrencyToAccountRecordPair(Currency currency) {
    // Currency pair must be used with usd
    // This is due to https://github.com/okcoin-okex/API-docs-OKEx.com/issues/115
    return adaptSymbol(new CurrencyPair(currency, Currency.USD));
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

    Map<String, Balance.Builder> builders = new TreeMap<>();

    for (Map.Entry<String, BigDecimal> available : funds.getFree().entrySet()) {
      builders.put(
          available.getKey(),
          new Balance.Builder()
              .currency(Currency.getInstance(available.getKey()))
              .available(available.getValue()));
    }

    for (Map.Entry<String, BigDecimal> frozen : funds.getFreezed().entrySet()) {
      Balance.Builder builder = builders.get(frozen.getKey());
      if (builder == null) {
        builder = new Balance.Builder().currency(Currency.getInstance(frozen.getKey()));
      }
      builders.put(frozen.getKey(), builder.frozen(frozen.getValue()));
    }

    for (Map.Entry<String, BigDecimal> borrowed : funds.getBorrow().entrySet()) {
      Balance.Builder builder = builders.get(borrowed.getKey());
      if (builder == null) {
        builder = new Balance.Builder().currency(Currency.getInstance(borrowed.getKey()));
      }
      builders.put(borrowed.getKey(), builder.borrowed(borrowed.getValue()));
    }

    List<Balance> wallet = new ArrayList<>(builders.size());

    for (Balance.Builder builder : builders.values()) {
      wallet.add(builder.build());
    }

    return new AccountInfo(Wallet.Builder.from(wallet).build());
  }

  public static AccountInfo adaptAccountInfoFutures(OkCoinFuturesUserInfoCross futureUserInfo) {
    OkcoinFuturesFundsCross btcFunds = futureUserInfo.getFunds(Currency.BTC);
    OkcoinFuturesFundsCross ltcFunds = futureUserInfo.getFunds(Currency.LTC);
    OkcoinFuturesFundsCross bchFunds = futureUserInfo.getFunds(Currency.BCH);

    Balance btcBalance = new Balance(BTC, btcFunds.getAccountRights());
    Balance ltcBalance = new Balance(LTC, ltcFunds.getAccountRights());
    Balance bchBalance = new Balance(BCH, bchFunds.getAccountRights());

    return new AccountInfo(
        Wallet.Builder.from(
                Stream.of(zeroUsdBalance, btcBalance, ltcBalance, bchBalance)
                    .collect(Collectors.toList()))
            .build());
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

    return new Trade.Builder()
        .type(trade.getType().equals("buy") ? OrderType.BID : OrderType.ASK)
        .originalAmount(trade.getAmount())
        .currencyPair(currencyPair)
        .price(trade.getPrice())
        .timestamp(trade.getDate())
        .id("" + trade.getTid())
        .build();
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
    return new UserTrade.Builder()
        .type(adaptOrderType(order.getType()))
        .originalAmount(order.getDealAmount())
        .currencyPair(adaptSymbol(order.getSymbol()))
        .price(order.getAveragePrice())
        .timestamp(order.getCreateDate())
        .id(tradeId)
        .orderId(orderId)
        .build();
  }

  private static UserTrade adaptTradeFutures(OkCoinFuturesOrder order) {

    return new UserTrade.Builder()
        .type(adaptOrderType(order.getType()))
        .originalAmount(order.getDealAmount())
        .currencyPair(adaptSymbol(order.getSymbol()))
        .price(order.getPrice())
        .timestamp(order.getCreatedDate())
        .orderId(String.valueOf(order.getOrderId()))
        .build();
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

      BigDecimal feeAmount = BigDecimal.ZERO;
      UserTrade trade =
          new UserTrade.Builder()
              .type(orderType)
              .originalAmount(originalAmount)
              .currencyPair(currencyPair)
              .price(price)
              .timestamp(timestamp)
              .id(tradeId)
              .orderId(orderId)
              .feeAmount(feeAmount)
              .feeCurrency(Currency.getInstance(currencyPair.counter.getCurrencyCode()))
              .build();

      trades.add(trade);
    }

    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  private static Date adaptDate(long date) {
    return DateUtils.fromMillisUtc(date);
  }

  public static List<FundingRecord> adaptFundingHistory(
      final OkCoinAccountRecords okCoinAccountRecordsList, FundingRecord.Type type) {
    final List<FundingRecord> fundingRecords = new ArrayList<>();
    if (okCoinAccountRecordsList != null) {
      final Currency c = Currency.getInstance(okCoinAccountRecordsList.getSymbol());
      for (OkCoinRecords okCoinRecordEntry : okCoinAccountRecordsList.getRecords()) {

        FundingRecord.Status status = null;
        if (okCoinRecordEntry.getStatus() != null) {
          if (type == Type.DEPOSIT) {
            final OkCoinRecords.RechargeStatus rechargeStatus =
                OkCoinRecords.RechargeStatus.fromInt(okCoinRecordEntry.getStatus());
            if (rechargeStatus != null) {
              status = FundingRecord.Status.resolveStatus(rechargeStatus.getStatus());
            }
          } else { // WITHDRAWAL
            final OkCoinRecords.WithdrawalStatus withdrawalStatus =
                OkCoinRecords.WithdrawalStatus.fromInt(okCoinRecordEntry.getStatus());
            if (withdrawalStatus != null) {
              status = FundingRecord.Status.resolveStatus(withdrawalStatus.getStatus());
            }
          }
        }

        fundingRecords.add(
            new FundingRecord(
                okCoinRecordEntry.getAddress(),
                adaptDate(okCoinRecordEntry.getDate()),
                c,
                okCoinRecordEntry.getAmount(),
                null,
                null,
                type,
                status,
                null,
                okCoinRecordEntry.getFee(),
                null));
      }
    }

    return fundingRecords;
  }
}
