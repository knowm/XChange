package org.knowm.xchange.bittrex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.account.BittrexDepositHistory;
import org.knowm.xchange.bittrex.dto.account.BittrexOrder;
import org.knowm.xchange.bittrex.dto.account.BittrexWithdrawalHistory;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexLevel;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.dto.trade.BittrexLimitOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOpenOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BittrexAdapters {

  public static final Logger log = LoggerFactory.getLogger(BittrexAdapters.class);

  private BittrexAdapters() {}

  public static List<org.knowm.xchange.currency.CurrencyPair> adaptCurrencyPairs(
      Collection<BittrexSymbol> bittrexSymbol) {

    List<org.knowm.xchange.currency.CurrencyPair> currencyPairs = new ArrayList<>();
    for (BittrexSymbol symbol : bittrexSymbol) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static org.knowm.xchange.currency.CurrencyPair adaptCurrencyPair(
      BittrexSymbol bittrexSymbol) {

    String baseSymbol = bittrexSymbol.getMarketCurrency();
    String counterSymbol = bittrexSymbol.getBaseCurrency();
    return org.knowm.xchange.currency.CurrencyPair.build(baseSymbol, counterSymbol);
  }

  public static List<LimitOrder> adaptOpenOrders(List<BittrexOpenOrder> bittrexOpenOrders) {

    List<LimitOrder> openOrders = new ArrayList<>();

    for (BittrexOpenOrder order : bittrexOpenOrders) {
      openOrders.add(adaptOpenOrder(order));
    }

    return openOrders;
  }

  public static BittrexLimitOrder adaptOpenOrder(BittrexOpenOrder bittrexOpenOrder) {

    OrderType type =
        bittrexOpenOrder.getOrderType().equalsIgnoreCase("LIMIT_SELL")
            ? OrderType.ASK
            : OrderType.BID;
    String[] currencies = bittrexOpenOrder.getExchange().split("-");
    org.knowm.xchange.currency.CurrencyPair pair =
        org.knowm.xchange.currency.CurrencyPair.build(currencies[1], currencies[0]);

    return new BittrexLimitOrder(
        type,
        bittrexOpenOrder.getQuantity(),
        pair,
        bittrexOpenOrder.getOrderUuid(),
        BittrexUtils.toDate(bittrexOpenOrder.getOpened()),
        bittrexOpenOrder.getLimit(),
        bittrexOpenOrder.getQuantityRemaining(),
        null,
        bittrexOpenOrder.getPricePerUnit());
  }

  public static List<LimitOrder> adaptOrders(
      BittrexLevel[] orders,
      org.knowm.xchange.currency.CurrencyPair currencyPair,
      String orderType,
      String id) {

    if (orders == null) {
      return new ArrayList<>();
    }

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);

    for (BittrexLevel order : orders) {
      limitOrders.add(adaptOrder(order.getAmount(), order.getPrice(), currencyPair, orderType, id));
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrder(
      BigDecimal amount,
      BigDecimal price,
      org.knowm.xchange.currency.CurrencyPair currencyPair,
      String orderTypeString,
      String id) {

    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  public static LimitOrder adaptOrder(BittrexOrder order) {
    OrderType type = order.getType().equalsIgnoreCase("LIMIT_SELL") ? OrderType.ASK : OrderType.BID;
    String[] currencies = order.getExchange().split("-");
    org.knowm.xchange.currency.CurrencyPair pair =
        org.knowm.xchange.currency.CurrencyPair.build(currencies[1], currencies[0]);

    OrderStatus status = OrderStatus.NEW;

    BigDecimal qty = order.getQuantity();
    BigDecimal qtyRem =
        order.getQuantityRemaining() != null ? order.getQuantityRemaining() : order.getQuantity();
    Boolean isOpen = order.getIsOpen();
    Boolean isCancelling = order.getCancelInitiated();
    int qtyRemainingToQty = qtyRem.compareTo(qty);
    int qtyRemainingIsZero = qtyRem.compareTo(BigDecimal.ZERO);

    if (isOpen && !isCancelling && qtyRemainingToQty < 0) {
      /* The order is open and remaining quantity less than order quantity */
      status = OrderStatus.PARTIALLY_FILLED;
    } else if (!isOpen && !isCancelling && qtyRemainingIsZero <= 0) {
      /* The order is closed and remaining quantity is zero */
      status = OrderStatus.FILLED;
    } else if (isOpen && isCancelling) {
      /* The order is open and the isCancelling flag has been set */
      status = OrderStatus.PENDING_CANCEL;
    } else if (!isOpen && isCancelling) {
      /* The order is closed and the isCancelling flag has been set */
      status = OrderStatus.CANCELED;
    }

    return new BittrexLimitOrder(
        type,
        order.getQuantity(),
        pair,
        order.getOrderUuid(),
        order.getOpened(),
        order.getLimit(),
        order.getQuantityRemaining(),
        order.getPricePerUnit(),
        null,
        status);
  }

  public static Trade adaptTrade(
      BittrexTrade trade, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    OrderType orderType =
        trade.getOrderType().equalsIgnoreCase("BUY") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity();
    BigDecimal price = trade.getPrice();
    Date date = BittrexUtils.toDate(trade.getTimeStamp());
    final String tradeId = String.valueOf(trade.getId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(
      BittrexTrade[] trades, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(trades.length);
    long lastTradeId = 0;
    for (BittrexTrade trade : trades) {
      long tradeId = Long.valueOf(trade.getId());
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(
      BittrexMarketSummary marketSummary, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    BigDecimal last = marketSummary.getLast();
    BigDecimal bid = marketSummary.getBid();
    BigDecimal ask = marketSummary.getAsk();
    BigDecimal high = marketSummary.getHigh();
    BigDecimal low = marketSummary.getLow();
    BigDecimal volume = marketSummary.getVolume();

    Date timestamp = BittrexUtils.toDate(marketSummary.getTimeStamp());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static Wallet adaptWallet(List<BittrexBalance> balances) {

    List<Balance> wallets = new ArrayList<>(balances.size());

    for (BittrexBalance balance : balances) {
      wallets.add(
          new Builder()
              .setCurrency(Currency.valueOf(balance.getCurrency().toUpperCase()))
              .setTotal(balance.getBalance())
              .setAvailable(balance.getAvailable())
              .setFrozen(
                  balance
                      .getBalance()
                      .subtract(balance.getAvailable())
                      .subtract(balance.getPending()))
              .setBorrowed(BigDecimal.ZERO)
              .setLoaned(BigDecimal.ZERO)
              .setWithdrawing(BigDecimal.ZERO)
              .setDepositing(balance.getPending())
              .createBalance());
    }

    return Wallet.build(wallets);
  }

  public static org.knowm.xchange.dto.account.Balance adaptBalance(BittrexBalance balance) {
    return new Builder()
        .setCurrency(Currency.valueOf(balance.getCurrency().toUpperCase()))
        .setTotal(balance.getBalance())
        .setAvailable(balance.getAvailable())
        .setFrozen(
            balance.getBalance().subtract(balance.getAvailable()).subtract(balance.getPending()))
        .setBorrowed(BigDecimal.ZERO)
        .setLoaned(BigDecimal.ZERO)
        .setWithdrawing(BigDecimal.ZERO)
        .setDepositing(balance.getPending())
        .createBalance();
  }

  public static List<UserTrade> adaptUserTrades(List<BittrexUserTrade> bittrexUserTrades) {

    List<UserTrade> trades = new ArrayList<>();

    for (BittrexUserTrade bittrexTrade : bittrexUserTrades) {
      trades.add(adaptUserTrade(bittrexTrade));
    }
    return trades;
  }

  public static UserTrade adaptUserTrade(BittrexUserTrade trade) {

    String[] currencies = trade.getExchange().split("-");
    org.knowm.xchange.currency.CurrencyPair currencyPair =
        org.knowm.xchange.currency.CurrencyPair.build(currencies[1], currencies[0]);

    OrderType orderType =
        trade.getOrderType().equalsIgnoreCase("LIMIT_BUY") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity().subtract(trade.getQuantityRemaining());
    Date date = BittrexUtils.toDate(trade.getClosed());
    String orderId = String.valueOf(trade.getOrderUuid());

    BigDecimal price = trade.getPricePerUnit();

    if (price == null) {
      price = trade.getLimit();
    }

    return new UserTrade(
        orderType,
        amount,
        currencyPair,
        price,
        date,
        orderId,
        orderId,
        trade.getCommission(),
        currencyPair.getCounter());
  }

  public static ExchangeMetaData adaptMetaData(
      List<BittrexSymbol> rawSymbols, ExchangeMetaData metaData) {

    List<org.knowm.xchange.currency.CurrencyPair> currencyPairs =
        BittrexAdapters.adaptCurrencyPairs(rawSymbols);

    Map<org.knowm.xchange.currency.CurrencyPair, CurrencyPairMetaData> pairsMap =
        metaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currenciesMap = metaData.getCurrencies();
    for (org.knowm.xchange.currency.CurrencyPair c : currencyPairs) {
      if (!pairsMap.containsKey(c)) {
        pairsMap.put(c, null);
      }
      if (!currenciesMap.containsKey(c.getBase())) {
        currenciesMap.put(c.getBase(), null);
      }
      if (!currenciesMap.containsKey(c.getCounter())) {
        currenciesMap.put(c.getCounter(), null);
      }
    }

    return metaData;
  }

  public static List<FundingRecord> adaptDepositRecords(
      List<BittrexDepositHistory> bittrexFundingHistories) {
    final ArrayList<FundingRecord> fundingRecords = new ArrayList<>();
    for (BittrexDepositHistory f : bittrexFundingHistories) {
      if (f != null) {
        fundingRecords.add(
            new FundingRecord(
                f.getCryptoAddress(),
                f.getLastUpdated(),
                Currency.valueOf(f.getCurrency()),
                f.getAmount(),
                String.valueOf(f.getId()),
                f.getTxId(),
                Type.DEPOSIT,
                Status.COMPLETE,
                null,
                null,
                null));
      }
    }
    return fundingRecords;
  }

  private static Status fromWithdrawalRecord(BittrexWithdrawalHistory bittrexWithdrawal) {
    if (bittrexWithdrawal.getCanceled()) return Status.CANCELLED;
    if (bittrexWithdrawal.getInvalidAddress()) return Status.FAILED;
    if (bittrexWithdrawal.getPendingPayment()) return Status.PROCESSING;
    if (bittrexWithdrawal.getAuthorized()) return Status.COMPLETE;
    return Status.FAILED;
  }

  public static List<FundingRecord> adaptWithdrawalRecords(
      List<BittrexWithdrawalHistory> bittrexFundingHistories) {
    final ArrayList<FundingRecord> fundingRecords = new ArrayList<>();
    for (BittrexWithdrawalHistory f : bittrexFundingHistories) {
      if (f != null) {
        final Status status = fromWithdrawalRecord(f);
        fundingRecords.add(
            new FundingRecord(
                f.getAddress(),
                f.getOpened(),
                Currency.valueOf(f.getCurrency()),
                f.getAmount(),
                f.getPaymentUuid(),
                f.getTxId(),
                Type.WITHDRAWAL,
                status,
                null,
                f.getTxCost(),
                null));
      }
    }
    return fundingRecords;
  }
}
