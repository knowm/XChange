package org.knowm.xchange.bitstamp;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatus;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderTransaction;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction.TransactionType;
import org.knowm.xchange.bitstamp.order.dto.BitstampGenericOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.utils.DateUtils;

/** Various adapters for converting from Bitstamp DTOs to XChange DTOs */
public final class BitstampAdapters {

  /** private Constructor */
  private BitstampAdapters() {}

  /**
   * Adapts a BitstampBalance to an AccountInfo
   *
   * @param bitstampBalance The Bitstamp balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(BitstampBalance bitstampBalance, String userName) {

    // Adapt to XChange DTOs
    List<Balance> balances = new ArrayList<>();
    for (org.knowm.xchange.bitstamp.dto.account.BitstampBalance.Balance b :
        bitstampBalance.getBalances()) {
      org.knowm.xchange.dto.account.Balance xchangeBalance =
          new org.knowm.xchange.dto.account.Balance.Builder()
              .setCurrency(Currency.valueOf(b.getCurrency().toUpperCase()))
              .setTotal(b.getBalance())
              .setAvailable(b.getAvailable())
              .setFrozen(b.getReserved())
              .setBorrowed(ZERO)
              .setLoaned(ZERO)
              .setWithdrawing(b.getBalance().subtract(b.getAvailable()).subtract(b.getReserved()))
              .setDepositing(ZERO)
              .createBalance();
      balances.add(xchangeBalance);
    }
    return AccountInfo.build(userName, bitstampBalance.getFee(), Wallet.build(balances));
  }

  /**
   * Adapts a org.knowm.xchange.bitstamp.api.model.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      BitstampOrderBook bitstampOrderBook, org.knowm.xchange.currency.CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, bitstampOrderBook.getBids());
    return new OrderBook(bitstampOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      org.knowm.xchange.currency.CurrencyPair currencyPair,
      OrderType orderType,
      List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      org.knowm.xchange.currency.CurrencyPair currencyPair,
      List<BigDecimal> priceAndAmount,
      OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Bitstamp transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(
      BitstampTransaction[] transactions, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitstampTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(adaptTrade(tx, currencyPair, 1000));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a Transaction to a Trade Object
   *
   * @param tx The Bitstamp transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(
      BitstampTransaction tx, org.knowm.xchange.currency.CurrencyPair currencyPair, int timeScale) {

    OrderType orderType = tx.getType() == 0 ? OrderType.BID : OrderType.ASK;
    final String tradeId = String.valueOf(tx.getTid());
    Date date =
        DateUtils.fromMillisUtc(
            tx.getDate()
                * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new Trade(orderType, tx.getAmount(), currencyPair, tx.getPrice(), date, tradeId);
  }

  /**
   * Adapts a BitstampTicker to a Ticker Object
   *
   * @param bitstampTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(
      BitstampTicker bitstampTicker, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    BigDecimal last = bitstampTicker.getLast();
    BigDecimal bid = bitstampTicker.getBid();
    BigDecimal ask = bitstampTicker.getAsk();
    BigDecimal high = bitstampTicker.getHigh();
    BigDecimal low = bitstampTicker.getLow();
    BigDecimal vwap = bitstampTicker.getVwap();
    BigDecimal volume = bitstampTicker.getVolume();
    Date timestamp = new Date(bitstampTicker.getTimestamp() * 1000L);

    return new Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .vwap(vwap)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  /**
   * Adapt the user's trades
   *
   * @param bitstampUserTransactions
   * @return
   */
  public static UserTrades adaptTradeHistory(BitstampUserTransaction[] bitstampUserTransactions) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitstampUserTransaction t : bitstampUserTransactions) {
      if (!t.getType().equals(TransactionType.trade)) { // skip account deposits and withdrawals.
        continue;
      }
      final OrderType orderType;
      if (t.getCounterAmount().doubleValue() == 0.0) {
        orderType = t.getBaseAmount().doubleValue() < 0.0 ? OrderType.ASK : OrderType.BID;
      } else {
        orderType = t.getCounterAmount().doubleValue() > 0.0 ? OrderType.ASK : OrderType.BID;
      }

      long tradeId = t.getId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      final org.knowm.xchange.currency.CurrencyPair pair =
          org.knowm.xchange.currency.CurrencyPair.build(
              t.getBaseCurrency().toUpperCase(), t.getCounterCurrency().toUpperCase());
      UserTrade trade =
          new UserTrade(
              orderType,
              t.getBaseAmount().abs(),
              pair,
              t.getPrice().abs(),
              t.getDatetime(),
              Long.toString(tradeId),
              Long.toString(t.getOrderId()),
              t.getFee(),
              Currency.valueOf(t.getFeeCurrency().toUpperCase()));
      trades.add(trade);
    }
    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Entry<String, BigDecimal> findNonzeroAmount(BitstampUserTransaction transaction)
      throws ExchangeException {
    for (Entry<String, BigDecimal> entry : transaction.getAmounts().entrySet()) {
      if (entry.getValue().abs().compareTo(new BigDecimal(1e-6)) == 1) {
        return entry;
      }
    }
    throw new ExchangeException(
        "Could not find non-zero amount in transaction (id: " + transaction.getId() + ')');
  }

  public static List<FundingRecord> adaptFundingHistory(
      List<BitstampUserTransaction> userTransactions) {
    List<FundingRecord> fundingRecords = new ArrayList<>();
    for (BitstampUserTransaction trans : userTransactions) {
      if (trans.isDeposit() || trans.isWithdrawal()) {
        Type type = trans.isDeposit() ? Type.DEPOSIT : Type.WITHDRAWAL;
        Entry<String, BigDecimal> amount = BitstampAdapters.findNonzeroAmount(trans);
        FundingRecord record =
            new FundingRecord(
                null,
                trans.getDatetime(),
                Currency.valueOf(amount.getKey()),
                amount.getValue().abs(),
                String.valueOf(trans.getId()),
                null,
                type,
                Status.COMPLETE,
                null,
                trans.getFee(),
                null);
        fundingRecords.add(record);
      }
    }
    return fundingRecords;
  }

  private static org.knowm.xchange.currency.CurrencyPair adaptCurrencyPair(
      BitstampOrderTransaction transaction) {

    // USD section
    if (transaction.getBtc() != null && transaction.getUsd() != null)
      return org.knowm.xchange.currency.CurrencyPair.BTC_USD;

    if (transaction.getLtc() != null && transaction.getUsd() != null)
      return org.knowm.xchange.currency.CurrencyPair.LTC_USD;

    if (transaction.getEth() != null && transaction.getUsd() != null)
      return org.knowm.xchange.currency.CurrencyPair.ETH_USD;

    if (transaction.getXrp() != null && transaction.getUsd() != null)
      return org.knowm.xchange.currency.CurrencyPair.XRP_USD;

    if (transaction.getBch() != null && transaction.getUsd() != null)
      return org.knowm.xchange.currency.CurrencyPair.BCH_USD;

    // EUR section
    if (transaction.getBtc() != null && transaction.getEur() != null)
      return org.knowm.xchange.currency.CurrencyPair.BTC_EUR;

    if (transaction.getLtc() != null && transaction.getEur() != null)
      return org.knowm.xchange.currency.CurrencyPair.LTC_EUR;

    if (transaction.getEth() != null && transaction.getEur() != null)
      return org.knowm.xchange.currency.CurrencyPair.ETH_EUR;

    if (transaction.getXrp() != null && transaction.getEur() != null)
      return org.knowm.xchange.currency.CurrencyPair.XRP_EUR;

    if (transaction.getBch() != null && transaction.getEur() != null)
      return org.knowm.xchange.currency.CurrencyPair.BCH_EUR;

    // BTC section
    if (transaction.getLtc() != null && transaction.getBtc() != null)
      return org.knowm.xchange.currency.CurrencyPair.LTC_BTC;

    if (transaction.getEth() != null && transaction.getBtc() != null)
      return org.knowm.xchange.currency.CurrencyPair.ETH_BTC;

    if (transaction.getXrp() != null && transaction.getBtc() != null)
      return org.knowm.xchange.currency.CurrencyPair.XRP_BTC;

    if (transaction.getBch() != null && transaction.getBtc() != null)
      return org.knowm.xchange.currency.CurrencyPair.BCH_BTC;

    if (transaction.getBch() != null && transaction.getBtc() != null)
      return org.knowm.xchange.currency.CurrencyPair.BCH_BTC;

    throw new NotYetImplementedForExchangeException();
  }

  private static BigDecimal getBaseCurrencyAmountFromBitstampTransaction(
      BitstampOrderTransaction bitstampTransaction) {

    org.knowm.xchange.currency.CurrencyPair currencyPair = adaptCurrencyPair(bitstampTransaction);

    if (currencyPair.getBase().equals(Currency.LTC)) return bitstampTransaction.getLtc();

    if (currencyPair.getBase().equals(Currency.BTC)) return bitstampTransaction.getBtc();

    if (currencyPair.getBase().equals(Currency.BCH)) return bitstampTransaction.getBch();

    if (currencyPair.getBase().equals(Currency.ETH)) return bitstampTransaction.getEth();

    if (currencyPair.getBase().equals(Currency.XRP)) return bitstampTransaction.getXrp();

    throw new NotYetImplementedForExchangeException();
  }

  public static OrderStatus adaptOrderStatus(BitstampOrderStatus bitstampOrderStatus) {

    if (bitstampOrderStatus.equals(BitstampOrderStatus.Queue)) return OrderStatus.PENDING_NEW;

    if (bitstampOrderStatus.equals(BitstampOrderStatus.Finished)) return OrderStatus.FILLED;

    if (bitstampOrderStatus.equals(BitstampOrderStatus.Open)) return OrderStatus.NEW;

    throw new NotYetImplementedForExchangeException();
  }

  /**
   * There is no method to discern market versus limit order type - so this returns a generic
   * BitstampGenericOrder as a status
   *
   * @param bitstampOrderStatusResponse
   * @return
   */
  public static BitstampGenericOrder adaptOrder(
      String orderId, BitstampOrderStatusResponse bitstampOrderStatusResponse) {

    BitstampOrderTransaction[] bitstampTransactions = bitstampOrderStatusResponse.getTransactions();

    // Use only the first transaction, because we assume that for a single order id all transactions
    // will
    // be of the same currency pair
    org.knowm.xchange.currency.CurrencyPair currencyPair =
        adaptCurrencyPair(bitstampTransactions[0]);
    Date date = bitstampTransactions[0].getDatetime();

    BigDecimal averagePrice =
        Arrays.stream(bitstampTransactions)
            .map(BitstampOrderTransaction::getPrice)
            .reduce(BigDecimal::add)
            .get()
            .divide(BigDecimal.valueOf(bitstampTransactions.length), RoundingMode.CEILING);

    BigDecimal cumulativeAmount =
        Arrays.stream(bitstampTransactions)
            .map(BitstampAdapters::getBaseCurrencyAmountFromBitstampTransaction)
            .reduce(BigDecimal::add)
            .get();

    BigDecimal totalFee =
        Arrays.stream(bitstampTransactions)
            .map(BitstampOrderTransaction::getFee)
            .reduce(BigDecimal::add)
            .get();

    OrderStatus orderStatus = adaptOrderStatus(bitstampOrderStatusResponse.getStatus());

    BitstampGenericOrder bitstampGenericOrder =
        new BitstampGenericOrder(
            null, // not discernable from response data
            null, // not discernable from the data
            currencyPair,
            orderId,
            date,
            averagePrice,
            cumulativeAmount,
            totalFee,
            orderStatus);

    return bitstampGenericOrder;
  }
}
