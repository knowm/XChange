package org.knowm.xchange.itbit.v1;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.itbit.v1.dto.ItBitFunding;
import org.knowm.xchange.itbit.v1.dto.account.ItBitAccountBalance;
import org.knowm.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTrade;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTrades;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitOrder;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitTradeHistory;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitUserTrade;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitUserTrade.Direction;
import org.knowm.xchange.utils.DateUtils;

public final class ItBitAdapters {

  private static final OpenOrders noOpenOrders =
      new OpenOrders(Collections.<LimitOrder>emptyList());
  private static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final DecimalFormatSymbols CUSTOM_SYMBOLS = new DecimalFormatSymbols();
  private static Pattern TIMESTAMP_PATTERN = Pattern.compile("(.*\\.[0-9]{3})0000Z$");

  static {
    CUSTOM_SYMBOLS.setDecimalSeparator('.');
  }

  /** private Constructor */
  private ItBitAdapters() {}

  private static DateFormat getDateFormat() {
    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormat;
  }

  private static DecimalFormat getCryptoFormat() {
    DecimalFormat cryptoFormat = new DecimalFormat();
    cryptoFormat.setDecimalFormatSymbols(CUSTOM_SYMBOLS);
    cryptoFormat.setMaximumFractionDigits(4);
    cryptoFormat.setGroupingUsed(false);
    cryptoFormat.setRoundingMode(RoundingMode.HALF_UP);
    return cryptoFormat;
  }

  private static DecimalFormat getFiatFormat() {
    DecimalFormat fiatFormat = new DecimalFormat();
    fiatFormat.setDecimalFormatSymbols(CUSTOM_SYMBOLS);
    fiatFormat.setMaximumFractionDigits(2);
    fiatFormat.setGroupingUsed(false);
    fiatFormat.setRoundingMode(RoundingMode.HALF_UP);
    return fiatFormat;
  }

  private static Date parseDate(String date) {

    Date parse;
    try {
      parse = getDateFormat().parse(date.substring(0, 23) + 'Z');
    } catch (ParseException e) {
      return null;
    }

    return parse;
  }

  public static Trades adaptTrades(
      ItBitTrades trades, org.knowm.xchange.currency.CurrencyPair currencyPair)
      throws InvalidFormatException {

    List<Trade> tradesList = new ArrayList<>(trades.getCount());
    long lastMatchNumber = 0;
    for (int i = 0; i < trades.getCount(); i++) {
      ItBitTrade trade = trades.getTrades()[i];
      long matchNumber = trade.getMatchNumber();
      if (matchNumber > lastMatchNumber) lastMatchNumber = matchNumber;
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastMatchNumber, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(
      ItBitTrade trade, org.knowm.xchange.currency.CurrencyPair currencyPair)
      throws InvalidFormatException {
    String timestamp = trade.getTimestamp();

    // matcher instantiated each time for adaptTrade to be thread-safe
    Matcher matcher = TIMESTAMP_PATTERN.matcher(timestamp);
    // truncate sub-millisecond zeros
    if (matcher.matches()) {
      timestamp = matcher.group(1) + 'Z';
    }
    Date date = DateUtils.fromISODateString(timestamp);
    final String matchNumber = String.valueOf(trade.getMatchNumber());

    return new Trade(null, trade.getAmount(), currencyPair, trade.getPrice(), date, matchNumber);
  }

  public static List<LimitOrder> adaptOrders(
      List<BigDecimal[]> orders,
      org.knowm.xchange.currency.CurrencyPair currencyPair,
      OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    if (orders == null) return limitOrders;

    for (BigDecimal[] level : orders) {
      limitOrders.add(adaptOrder(level[1], level[0], currencyPair, null, orderType, null));
    }

    return limitOrders;
  }

  private static LimitOrder adaptOrder(
      BigDecimal amount,
      BigDecimal price,
      org.knowm.xchange.currency.CurrencyPair currencyPair,
      String orderId,
      OrderType orderType,
      Date timestamp) {

    return new LimitOrder(orderType, amount, currencyPair, orderId, timestamp, price);
  }

  public static AccountInfo adaptAccountInfo(ItBitAccountInfoReturn[] info) {

    List<Wallet> wallets = new ArrayList<>(info.length);
    String userId = "";

    for (ItBitAccountInfoReturn itBitAccountInfoReturn : info) {
      ItBitAccountBalance[] balances = itBitAccountInfoReturn.getBalances();

      userId = itBitAccountInfoReturn.getUserId();

      List<Balance> walletContent = new ArrayList<>(balances.length);

      for (ItBitAccountBalance itBitAccountBalance : balances) {
        Currency currency = adaptCcy(itBitAccountBalance.getCurrency());
        BigDecimal total = itBitAccountBalance.getTotalBalance();
        BigDecimal available = itBitAccountBalance.getAvailableBalance();
        Balance balance =
            new Balance.Builder()
                .setCurrency(currency)
                .setTotal(total)
                .setAvailable(available)
                .setFrozen(total.add(available.negate()))
                .createBalance();
        walletContent.add(balance);
      }

      Wallet wallet =
          Wallet.build(
              itBitAccountInfoReturn.getId(), itBitAccountInfoReturn.getName(), walletContent);
      wallets.add(wallet);
    }

    return AccountInfo.build(userId, wallets);
  }

  public static OpenOrders adaptPrivateOrders(ItBitOrder[] orders) {

    if (orders.length <= 0) {
      return noOpenOrders;
    }

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);

    for (ItBitOrder itBitOrder : orders) {
      String instrument = itBitOrder.getInstrument();

      org.knowm.xchange.currency.CurrencyPair currencyPair =
          org.knowm.xchange.currency.CurrencyPair.build(
              instrument.substring(0, 3), instrument.substring(3, 6));
      OrderType orderType = itBitOrder.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      Date timestamp = parseDate(itBitOrder.getCreatedTime());
      limitOrders.add(
          adaptOrder(
              itBitOrder.getAmount(),
              itBitOrder.getPrice(),
              currencyPair,
              itBitOrder.getId(),
              orderType,
              timestamp));
    }

    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(ItBitTradeHistory history) {
    List<ItBitUserTrade> itBitTrades = history.getTradingHistory();

    ListMultimap<String, ItBitUserTrade> tradesByOrderId = ArrayListMultimap.create();

    for (ItBitUserTrade itBitTrade : itBitTrades) {
      tradesByOrderId.put(itBitTrade.getOrderId(), itBitTrade);
    }

    List<UserTrade> trades = new ArrayList<>();

    for (String orderId : tradesByOrderId.keySet()) {
      BigDecimal totalValue = BigDecimal.ZERO;
      BigDecimal totalQuantity = BigDecimal.ZERO;
      BigDecimal totalFee = BigDecimal.ZERO;

      for (ItBitUserTrade trade : tradesByOrderId.get(orderId)) {
        // can have multiple trades for same order, so add them all up here to
        // get the average price and total fee
        // we have to do this because there is no trade id
        totalValue = totalValue.add(trade.getCurrency1Amount().multiply(trade.getRate()));
        totalQuantity = totalQuantity.add(trade.getCurrency1Amount());
        totalFee = totalFee.add(trade.getCommissionPaid());
      }

      BigDecimal volumeWeightedAveragePrice =
          totalValue.divide(totalQuantity, 8, RoundingMode.HALF_UP);

      ItBitUserTrade itBitTrade = tradesByOrderId.get(orderId).get(0);
      OrderType orderType =
          itBitTrade.getDirection().equals(Direction.buy) ? OrderType.BID : OrderType.ASK;

      org.knowm.xchange.currency.CurrencyPair currencyPair =
          adaptCcyPair(itBitTrade.getInstrument());
      Currency feeCcy = adaptCcy(itBitTrade.getCommissionCurrency());

      UserTrade userTrade =
          new UserTrade(
              orderType,
              totalQuantity,
              currencyPair,
              volumeWeightedAveragePrice,
              itBitTrade.getTimestamp(),
              orderId,
              // itbit doesn't have trade ids, so we use the order id instead
              orderId,
              totalFee,
              feeCcy);

      trades.add(userTrade);
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static org.knowm.xchange.currency.CurrencyPair adaptCcyPair(String instrument) {
    Currency base = adaptCcy(instrument.substring(0, 3));
    Currency counter = adaptCcy(instrument.substring(3, 6));
    return org.knowm.xchange.currency.CurrencyPair.build(base, counter);
  }

  public static Currency adaptCcy(String ccy) {
    if (ccy.toUpperCase().equals("XBT")) return Currency.BTC;

    return Currency.valueOf(ccy);
  }

  public static Ticker adaptTicker(
      org.knowm.xchange.currency.CurrencyPair currencyPair, ItBitTicker itBitTicker) {

    BigDecimal bid = itBitTicker.getBid();
    BigDecimal ask = itBitTicker.getAsk();
    BigDecimal high = itBitTicker.getHighToday();
    BigDecimal low = itBitTicker.getLowToday();
    BigDecimal last = itBitTicker.getLastPrice();
    BigDecimal volume = itBitTicker.getVolume24h();
    Date timestamp =
        itBitTicker.getTimestamp() != null ? parseDate(itBitTicker.getTimestamp()) : null;

    return new Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .bidSize(itBitTicker.getBidAmt())
        .askSize(itBitTicker.getAskAmt())
        .build();
  }

  public static String formatFiatAmount(BigDecimal amount) {
    return getFiatFormat().format(amount);
  }

  public static String formatCryptoAmount(BigDecimal amount) {
    return getCryptoFormat().format(amount);
  }

  public static org.knowm.xchange.currency.CurrencyPair adaptCurrencyPairToExchange(
      org.knowm.xchange.currency.CurrencyPair currencyPair) {
    return org.knowm.xchange.currency.CurrencyPair.build(
        adaptCurrencyToExchange(currencyPair.getBase()),
        adaptCurrencyToExchange(currencyPair.getCounter()));
  }

  public static Currency adaptCurrencyToExchange(Currency currency) {
    if (currency == Currency.BTC) {
      return currency.getIso4217Currency();
    }
    return currency;
  }

  public static FundingRecord adapt(ItBitFunding itBitFunding) {
    SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); // "2015-02-18T23:43:37.1230000"

    try {
      Date date = dateFormat.parse(itBitFunding.time);

      Type type =
          itBitFunding.transactionType.equalsIgnoreCase("Deposit") ? Type.DEPOSIT : Type.WITHDRAWAL;

      Status status = Status.PROCESSING;
      if (itBitFunding.status.equals("cancelled")) status = Status.CANCELLED;
      if (itBitFunding.status.equals("completed")) status = Status.COMPLETE;

      Currency currency = adaptCcy(itBitFunding.currency);

      return new FundingRecord(
          itBitFunding.destinationAddress,
          date,
          currency,
          itBitFunding.amount,
          itBitFunding.withdrawalId,
          itBitFunding.txnHash,
          type,
          status,
          null,
          null,
          null);
    } catch (ParseException e) {
      throw new IllegalStateException("Cannot parse " + itBitFunding, e);
    }
  }
}
