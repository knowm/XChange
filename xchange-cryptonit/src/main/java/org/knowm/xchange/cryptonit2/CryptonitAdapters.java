package org.knowm.xchange.cryptonit2;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitBalance;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitOrderBook;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTransaction;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderStatus;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderStatusResponse;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderTransaction;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.cryptonit2.order.dto.CryptonitGenericOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.utils.DateUtils;

/** Various adapters for converting from Cryptonit DTOs to XChange DTOs */
public final class CryptonitAdapters {

  /** private Constructor */
  private CryptonitAdapters() {}

  /**
   * Adapts a CryptonitBalance to an AccountInfo
   *
   * @param cryptonitBalance The Cryptonit balance
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(CryptonitBalance cryptonitBalance, String userName) {

    // Adapt to XChange DTOs
    List<Balance> balances = new ArrayList<>();
    for (CryptonitBalance.Balance b : cryptonitBalance.getBalances()) {
      Balance xchangeBalance =
          new Balance(
              Currency.getInstance(b.getCurrency().toUpperCase()),
              b.getBalance(),
              b.getAvailable(),
              b.getReserved(),
              ZERO,
              ZERO,
              b.getBalance().subtract(b.getAvailable()).subtract(b.getReserved()),
              ZERO);
      balances.add(xchangeBalance);
    }
    return new AccountInfo(
        userName, cryptonitBalance.getFee(), Wallet.Builder.from(balances).build());
  }

  /**
   * Adapts a org.knowm.xchange.cryptonit2.api.model.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      CryptonitOrderBook cryptonitOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, cryptonitOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, cryptonitOrderBook.getBids());
    return new OrderBook(cryptonitOrderBook.getTimestamp(), asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {

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
   * @param transactions The Cryptonit transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(CryptonitTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (CryptonitTransaction tx : transactions) {
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
   * @param tx The Cryptonit transaction
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange Trade
   */
  public static Trade adaptTrade(
      CryptonitTransaction tx, CurrencyPair currencyPair, int timeScale) {

    OrderType orderType = tx.getType() == 0 ? OrderType.BID : OrderType.ASK;
    final String tradeId = String.valueOf(tx.getTid());
    Date date =
        DateUtils.fromMillisUtc(
            tx.getDate()
                * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new Trade.Builder()
        .type(orderType)
        .originalAmount(tx.getAmount())
        .currencyPair(currencyPair)
        .price(tx.getPrice())
        .timestamp(date)
        .id(tradeId)
        .build();
  }

  /**
   * Adapts a CryptonitTicker to a Ticker Object
   *
   * @param cryptonitTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CryptonitTicker cryptonitTicker, CurrencyPair currencyPair) {

    BigDecimal open = cryptonitTicker.getOpen();
    BigDecimal last = cryptonitTicker.getLast();
    BigDecimal bid = cryptonitTicker.getBid();
    BigDecimal ask = cryptonitTicker.getAsk();
    BigDecimal high = cryptonitTicker.getHigh();
    BigDecimal low = cryptonitTicker.getLow();
    BigDecimal vwap = cryptonitTicker.getVwap();
    BigDecimal volume = cryptonitTicker.getVolume();
    Date timestamp = new Date(cryptonitTicker.getTimestamp() * 1000L);

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .open(open)
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

  /** Adapt the user's trades */
  public static UserTrades adaptTradeHistory(CryptonitUserTransaction[] cryptonitUserTransactions) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (CryptonitUserTransaction t : cryptonitUserTransactions) {
      if (!t.getType()
          .equals(
              CryptonitUserTransaction.TransactionType
                  .trade)) { // skip account deposits and withdrawals.
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
      final CurrencyPair pair =
          new CurrencyPair(t.getBaseCurrency().toUpperCase(), t.getCounterCurrency().toUpperCase());
      UserTrade trade =
          new UserTrade.Builder()
              .type(orderType)
              .originalAmount(t.getBaseAmount().abs())
              .currencyPair(pair)
              .price(t.getPrice().abs())
              .timestamp(t.getDatetime())
              .id(Long.toString(tradeId))
              .orderId(Long.toString(t.getOrderId()))
              .feeAmount(t.getFee())
              .feeCurrency(Currency.getInstance(t.getFeeCurrency().toUpperCase()))
              .build();
      trades.add(trade);
    }
    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Map.Entry<String, BigDecimal> findNonzeroAmount(
      CryptonitUserTransaction transaction) throws ExchangeException {
    for (Map.Entry<String, BigDecimal> entry : transaction.getAmounts().entrySet()) {
      if (entry.getValue().abs().compareTo(new BigDecimal(1e-6)) == 1) {
        return entry;
      }
    }
    throw new ExchangeException(
        "Could not find non-zero amount in transaction (id: " + transaction.getId() + ")");
  }

  public static List<FundingRecord> adaptFundingHistory(
      List<CryptonitUserTransaction> userTransactions) {
    List<FundingRecord> fundingRecords = new ArrayList<>();
    for (CryptonitUserTransaction trans : userTransactions) {
      if (trans.isDeposit() || trans.isWithdrawal()) {
        FundingRecord.Type type =
            trans.isDeposit() ? FundingRecord.Type.DEPOSIT : FundingRecord.Type.WITHDRAWAL;
        Map.Entry<String, BigDecimal> amount = CryptonitAdapters.findNonzeroAmount(trans);
        FundingRecord record =
            new FundingRecord(
                null,
                trans.getDatetime(),
                Currency.getInstance(amount.getKey()),
                amount.getValue().abs(),
                String.valueOf(trans.getId()),
                null,
                type,
                FundingRecord.Status.COMPLETE,
                null,
                trans.getFee(),
                null);
        fundingRecords.add(record);
      }
    }
    return fundingRecords;
  }

  private static CurrencyPair adaptCurrencyPair(CryptonitOrderTransaction transaction) {

    // USD section
    if (transaction.getBtc() != null && transaction.getUsd() != null) {
      return CurrencyPair.BTC_USD;
    }

    if (transaction.getLtc() != null && transaction.getUsd() != null) {
      return CurrencyPair.LTC_USD;
    }

    if (transaction.getEth() != null && transaction.getUsd() != null) {
      return CurrencyPair.ETH_USD;
    }

    if (transaction.getXrp() != null && transaction.getUsd() != null) {
      return CurrencyPair.XRP_USD;
    }

    if (transaction.getBch() != null && transaction.getUsd() != null) {
      return CurrencyPair.BCH_USD;
    }

    // EUR section
    if (transaction.getBtc() != null && transaction.getEur() != null) {
      return CurrencyPair.BTC_EUR;
    }

    if (transaction.getLtc() != null && transaction.getEur() != null) {
      return CurrencyPair.LTC_EUR;
    }

    if (transaction.getEth() != null && transaction.getEur() != null) {
      return CurrencyPair.ETH_EUR;
    }

    if (transaction.getXrp() != null && transaction.getEur() != null) {
      return CurrencyPair.XRP_EUR;
    }

    if (transaction.getBch() != null && transaction.getEur() != null) {
      return CurrencyPair.BCH_EUR;
    }

    // BTC section
    if (transaction.getLtc() != null && transaction.getBtc() != null) {
      return CurrencyPair.LTC_BTC;
    }

    if (transaction.getEth() != null && transaction.getBtc() != null) {
      return CurrencyPair.ETH_BTC;
    }

    if (transaction.getXrp() != null && transaction.getBtc() != null) {
      return CurrencyPair.XRP_BTC;
    }

    if (transaction.getBch() != null && transaction.getBtc() != null) {
      return CurrencyPair.BCH_BTC;
    }

    if (transaction.getBch() != null && transaction.getBtc() != null) {
      return CurrencyPair.BCH_BTC;
    }

    throw new NotYetImplementedForExchangeException();
  }

  private static BigDecimal getBaseCurrencyAmountFromCryptonitTransaction(
      CryptonitOrderTransaction cryptonitTransaction) {

    CurrencyPair currencyPair = adaptCurrencyPair(cryptonitTransaction);

    if (currencyPair.base.equals(Currency.LTC)) {
      return cryptonitTransaction.getLtc();
    }

    if (currencyPair.base.equals(Currency.BTC)) {
      return cryptonitTransaction.getBtc();
    }

    if (currencyPair.base.equals(Currency.BCH)) {
      return cryptonitTransaction.getBch();
    }

    if (currencyPair.base.equals(Currency.ETH)) {
      return cryptonitTransaction.getEth();
    }

    if (currencyPair.base.equals(Currency.XRP)) {
      return cryptonitTransaction.getXrp();
    }

    throw new NotYetImplementedForExchangeException();
  }

  public static OrderStatus adaptOrderStatus(
      CryptonitOrderStatus cryptonitOrderStatus, int length) {

    if (cryptonitOrderStatus.equals(CryptonitOrderStatus.Queue)) {
      return OrderStatus.PENDING_NEW;
    }

    if (cryptonitOrderStatus.equals(CryptonitOrderStatus.Finished) && length > 0) {
      return OrderStatus.FILLED;
    } else if (cryptonitOrderStatus.equals(CryptonitOrderStatus.Finished) && length == 0) {
      return OrderStatus.CANCELED;
    }

    if (cryptonitOrderStatus.equals(CryptonitOrderStatus.Open) && length == 0) {
      return OrderStatus.NEW;
    } else if (cryptonitOrderStatus.equals(CryptonitOrderStatus.Open) && length > 0) {
      return OrderStatus.PARTIALLY_FILLED;
    }

    throw new NotYetImplementedForExchangeException();
  }

  /**
   * There is no method to discern market versus limit order type - so this returns a generic
   * CryptonitGenericOrder as a status
   */
  public static CryptonitGenericOrder adaptOrder(
      String orderId, CryptonitOrderStatusResponse cryptonitOrderStatusResponse) {

    CryptonitOrderTransaction[] cryptonitTransactions =
        cryptonitOrderStatusResponse.getTransactions();
    CurrencyPair currencyPair = null;
    Date date = null;
    BigDecimal averagePrice = null;
    BigDecimal cumulativeAmount = null;
    BigDecimal totalFee = null;
    // Use only the first transaction, because we assume that for a single order id all transactions
    // will
    // be of the same currency pair
    if (cryptonitTransactions.length > 0) {
      currencyPair = adaptCurrencyPair(cryptonitTransactions[0]);
      date = cryptonitTransactions[0].getDatetime();

      averagePrice =
          Arrays.stream(cryptonitTransactions)
              .map(t -> t.getPrice())
              .reduce((x, y) -> x.add(y))
              .get()
              .divide(BigDecimal.valueOf(cryptonitTransactions.length), 2);

      cumulativeAmount =
          Arrays.stream(cryptonitTransactions)
              .map(t -> getBaseCurrencyAmountFromCryptonitTransaction(t))
              .reduce((x, y) -> x.add(y))
              .get();

      totalFee =
          Arrays.stream(cryptonitTransactions)
              .map(t -> t.getFee())
              .reduce((x, y) -> x.add(y))
              .get();
    }

    OrderStatus orderStatus =
        adaptOrderStatus(cryptonitOrderStatusResponse.getStatus(), cryptonitTransactions.length);

    CryptonitGenericOrder cryptonitGenericOrder =
        new CryptonitGenericOrder(
            null, // not discernable from response data
            null, // not discernable from the data
            currencyPair,
            orderId,
            date,
            averagePrice,
            cumulativeAmount,
            totalFee,
            orderStatus);

    return cryptonitGenericOrder;
  }
}
