package org.knowm.xchange.btcmarkets;

import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransfer;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BTCMarketsAdapters {

  private static final Logger logger = LoggerFactory.getLogger(BTCMarketsAdapters.class);

  public static final Comparator<LimitOrder> ASK_COMPARATOR =
      new Comparator<LimitOrder>() {
        @Override
        public int compare(LimitOrder o1, LimitOrder o2) {
          return o1.getLimitPrice().compareTo(o2.getLimitPrice());
        }
      };
  public static final Comparator<LimitOrder> BID_COMPARATOR =
      new Comparator<LimitOrder>() {
        @Override
        public int compare(LimitOrder o1, LimitOrder o2) {
          return o2.getLimitPrice().compareTo(o1.getLimitPrice());
        }
      };

  private static final Map<String, Order.OrderStatus> orderStatusMap = new HashMap<>();

  static {
    orderStatusMap.put("New", Order.OrderStatus.NEW);
    orderStatusMap.put("Placed", Order.OrderStatus.NEW);
    orderStatusMap.put("Failed", Order.OrderStatus.STOPPED);
    orderStatusMap.put("Error", Order.OrderStatus.STOPPED);
    orderStatusMap.put("Cancelled", Order.OrderStatus.CANCELED);
    orderStatusMap.put("Partially Cancelled", Order.OrderStatus.PARTIALLY_CANCELED);
    orderStatusMap.put("Fully Matched", Order.OrderStatus.FILLED);
    orderStatusMap.put("Partially Matched", Order.OrderStatus.PARTIALLY_FILLED);
  }

  private BTCMarketsAdapters() {}

  public static Wallet adaptWallet(List<BTCMarketsBalance> balances) {
    List<Balance> wallets = new ArrayList<>(balances.size());
    for (BTCMarketsBalance blc : balances) {
      final Currency currency = Currency.getInstance(blc.getCurrency());
      wallets.add(new Balance(currency, blc.getBalance(), blc.getAvailable()));
    }
    return Wallet.Builder.from(wallets).build();
  }

  public static OrderBook adaptOrderBook(
      BTCMarketsOrderBook btcmarketsOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        createOrders(Order.OrderType.ASK, btcmarketsOrderBook.getAsks(), currencyPair);
    List<LimitOrder> bids =
        createOrders(Order.OrderType.BID, btcmarketsOrderBook.getBids(), currencyPair);
    Collections.sort(bids, BID_COMPARATOR);
    Collections.sort(asks, ASK_COMPARATOR);
    return new OrderBook(btcmarketsOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      Order.OrderType orderType, List<BigDecimal[]> orders, CurrencyPair currencyPair) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BigDecimal[] o : orders) {
      limitOrders.add(new LimitOrder(orderType, o[1], currencyPair, null, null, o[0]));
    }
    return limitOrders;
  }

  public static Order.OrderStatus adaptOrderStatus(String btcmarketsOrderStatus) {
    Order.OrderStatus result =
        orderStatusMap.getOrDefault(btcmarketsOrderStatus, Order.OrderStatus.UNKNOWN);
    if (result == Order.OrderStatus.UNKNOWN) {
      logger.warn("Unable to map btcmarkets orderStatus : {}", btcmarketsOrderStatus);
    }
    return result;
  }

  public static LimitOrder adaptOrder(BTCMarketsOrder o) {
    BigDecimal averagePrice =
        BigDecimal.valueOf(
            o.getTrades().stream()
                .mapToDouble(value -> value.getPrice().doubleValue())
                .summaryStatistics()
                .getAverage());
    BigDecimal fee =
        BigDecimal.valueOf(
            o.getTrades().stream().mapToDouble(value -> value.getFee().doubleValue()).sum());
    BigDecimal cumulativeAmount =
        BigDecimal.valueOf(
            o.getTrades().stream().mapToDouble(value -> value.getVolume().doubleValue()).sum());
    return new LimitOrder(
        adaptOrderType(o.getOrderSide()),
        o.getVolume(),
        new CurrencyPair(o.getInstrument(), o.getCurrency()),
        Long.toString(o.getId()),
        o.getCreationTime(),
        o.getPrice(),
        averagePrice,
        cumulativeAmount,
        fee,
        adaptOrderStatus(o.getStatus()));
  }

  public static UserTrades adaptTradeHistory(
      List<BTCMarketsUserTrade> btcmarketsUserTrades, CurrencyPair currencyPair) {
    List<UserTrade> trades = new ArrayList<>();
    for (BTCMarketsUserTrade btcmarketsUserTrade : btcmarketsUserTrades) {
      trades.add(adaptTrade(btcmarketsUserTrade, currencyPair));
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static UserTrade adaptTrade(BTCMarketsUserTrade trade, CurrencyPair currencyPair) {
    final Order.OrderType type = adaptOrderType(trade.getSide());
    final String tradeId = Long.toString(trade.getId());
    final Long orderId = trade.getOrderId();
    final String feeCurrency = currencyPair.base.getCurrencyCode();
    return new UserTrade.Builder()
        .type(type)
        .originalAmount(trade.getVolume())
        .currencyPair(currencyPair)
        .price(trade.getPrice().abs())
        .timestamp(trade.getCreationTime())
        .id(tradeId)
        .orderId(String.valueOf(orderId))
        .feeAmount(trade.getFee())
        .feeCurrency(Currency.getInstance(feeCurrency))
        .build();
  }

  public static Order.OrderType adaptOrderType(BTCMarketsOrder.Side orderType) {
    return orderType.equals(BTCMarketsOrder.Side.Bid) ? Order.OrderType.BID : Order.OrderType.ASK;
  }

  public static OpenOrders adaptOpenOrders(BTCMarketsOrders openOrders) {
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BTCMarketsOrder btcmarketsOrder : openOrders.getOrders()) {
      limitOrders.add(adaptOrder(btcmarketsOrder));
    }
    return new OpenOrders(limitOrders);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, BTCMarketsTicker t) {
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(t.getLastPrice())
        .bid(t.getBestBid())
        .ask(t.getBestAsk())
        .timestamp(t.getTimestamp())
        .build();
  }

  public static List<FundingRecord> adaptFundingHistory(
      BTCMarketsFundtransferHistoryResponse btcMarketsFundtransferHistoryResponse) {
    List<FundingRecord> result = new ArrayList<>();
    for (BTCMarketsFundtransfer transfer :
        btcMarketsFundtransferHistoryResponse.getFundTransfers()) {
      String address = null;
      String blockchainTransactionHash = null;
      if (transfer.getCryptoPaymentDetail() != null) {
        address = transfer.getCryptoPaymentDetail().getAddress();
        blockchainTransactionHash = transfer.getCryptoPaymentDetail().getTxId();
      }
      FundingRecord.Type fundingrecordType = null;
      if (transfer.getTransferType().equals("WITHDRAW")) {
        fundingrecordType = FundingRecord.Type.WITHDRAWAL;
      } else if (transfer.getTransferType().equals("DEPOSIT")) {
        fundingrecordType = FundingRecord.Type.DEPOSIT;
      }
      FundingRecord.Status fundingRecordStatus = null;
      if (transfer.getStatus().equals("Complete")) {
        fundingRecordStatus = FundingRecord.Status.COMPLETE;
      } else {
        fundingRecordStatus = FundingRecord.Status.PROCESSING;
      }

      result.add(
          new FundingRecord(
              address,
              transfer.getCreationTime(),
              Currency.getInstance(transfer.getCurrency()),
              transfer.getAmount(),
              Long.toString(transfer.getFundTransferId()),
              blockchainTransactionHash,
              fundingrecordType,
              fundingRecordStatus,
              null,
              transfer.getFee(),
              transfer.getDescription()));
    }
    return result;
  }
}
