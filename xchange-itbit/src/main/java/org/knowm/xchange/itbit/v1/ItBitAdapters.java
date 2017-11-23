package org.knowm.xchange.itbit.v1;

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
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.itbit.v1.dto.account.ItBitAccountBalance;
import org.knowm.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTrade;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTrades;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitOrder;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitTradeHistory;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitUserTrade;
import org.knowm.xchange.utils.DateUtils;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public final class ItBitAdapters {

  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder>emptyList());
  private static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final DecimalFormatSymbols CUSTOM_SYMBOLS = new DecimalFormatSymbols();
  private static Pattern TIMESTAMP_PATTERN = Pattern.compile("(.*\\.[0-9]{3})0000Z$");

  static {
    CUSTOM_SYMBOLS.setDecimalSeparator('.');
  }

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

  /**
   * private Constructor
   */
  private ItBitAdapters() {

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

  public static Trades adaptTrades(ItBitTrades trades, CurrencyPair currencyPair) throws InvalidFormatException {

    List<Trade> tradesList = new ArrayList<>(trades.getCount());
    long lastTradeId = 0;
    for (int i = 0; i < trades.getCount(); i++) {
      ItBitTrade trade = trades.getTrades()[i];
      long tradeId = trade.getTid();
      if (tradeId > lastTradeId)
        lastTradeId = tradeId;
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(ItBitTrade trade, CurrencyPair currencyPair) throws InvalidFormatException {
    String timestamp = trade.getTimestamp();

    //matcher instantiated each time for adaptTrade to be thread-safe
    Matcher matcher = TIMESTAMP_PATTERN.matcher(timestamp);
    //truncate sub-millisecond zeros
    if (matcher.matches()) {
      timestamp = matcher.group(1) + "Z";
    }
    Date date = DateUtils.fromISODateString(timestamp);
    final String tradeId = String.valueOf(trade.getTid());

    return new Trade(null, trade.getAmount(), currencyPair, trade.getPrice(), date, tradeId);
  }

  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    if (orders == null)
      return limitOrders;

    for (BigDecimal[] level : orders) {
      limitOrders.add(adaptOrder(level[1], level[0], currencyPair, null, orderType, null));
    }

    return limitOrders;

  }

  private static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderId, OrderType orderType,
      Date timestamp) {

    return new LimitOrder(orderType, amount, currencyPair, orderId, timestamp, price);
  }

  public static AccountInfo adaptAccountInfo(ItBitAccountInfoReturn[] info) {

    List<Wallet> wallets = new ArrayList<>(info.length);
    String userId = "";

    for (int i = 0; i < info.length; i++) {
      ItBitAccountInfoReturn itBitAccountInfoReturn = info[i];
      ItBitAccountBalance[] balances = itBitAccountInfoReturn.getBalances();

      userId = itBitAccountInfoReturn.getUserId();

      List<Balance> walletContent = new ArrayList<>(balances.length);

      for (int j = 0; j < balances.length; j++) {
        ItBitAccountBalance itBitAccountBalance = balances[j];

        Currency currency = Currency.getInstance(itBitAccountBalance.getCurrency());
        Balance balance = new Balance(currency, itBitAccountBalance.getTotalBalance(), itBitAccountBalance.getAvailableBalance());
        walletContent.add(balance);
      }

      Wallet wallet = new Wallet(itBitAccountInfoReturn.getId(), itBitAccountInfoReturn.getName(), walletContent);
      wallets.add(wallet);
    }

    return new AccountInfo(userId, wallets);
  }

  public static OpenOrders adaptPrivateOrders(ItBitOrder[] orders) {

    if (orders.length <= 0) {
      return noOpenOrders;
    }

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);

    for (int i = 0; i < orders.length; i++) {
      ItBitOrder itBitOrder = orders[i];
      String instrument = itBitOrder.getInstrument();

      CurrencyPair currencyPair = new CurrencyPair(instrument.substring(0, 3), instrument.substring(3, 6));
      OrderType orderType = itBitOrder.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      Date timestamp = parseDate(itBitOrder.getCreatedTime());
      limitOrders.add(adaptOrder(itBitOrder.getAmount(), itBitOrder.getPrice(), currencyPair, itBitOrder.getId(), orderType, timestamp));
    }

    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(ItBitTradeHistory history) {
    List<ItBitUserTrade> itBitTrades = history.getTradingHistory();

    List<UserTrade> trades = new ArrayList<>(itBitTrades.size());

    for (ItBitUserTrade itBitTrade : itBitTrades) {
      String instrument = itBitTrade.getInstrument();

      OrderType orderType = itBitTrade.getDirection().equals(ItBitUserTrade.Direction.buy) ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = new CurrencyPair(instrument.substring(0, 3), instrument.substring(3, 6));

      trades.add(new UserTrade(orderType, itBitTrade.getCurrency1Amount(), currencyPair, itBitTrade.getRate(), itBitTrade.getTimestamp(), null,
          itBitTrade.getOrderId(), itBitTrade.getCommissionPaid(), new Currency(itBitTrade.getCommissionCurrency())));
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, ItBitTicker itBitTicker) {

    BigDecimal bid = itBitTicker.getBid();
    BigDecimal ask = itBitTicker.getAsk();
    BigDecimal high = itBitTicker.getHighToday();
    BigDecimal low = itBitTicker.getLowToday();
    BigDecimal last = itBitTicker.getLastPrice();
    BigDecimal volume = itBitTicker.getVolume24h();
    Date timestamp = itBitTicker.getTimestamp() != null ? parseDate(itBitTicker.getTimestamp()) : null;

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();
  }

  public static String formatFiatAmount(BigDecimal amount) {
    return getFiatFormat().format(amount);
  }

  public static String formatCryptoAmount(BigDecimal amount) {
    return getCryptoFormat().format(amount);
  }

  public static CurrencyPair adaptCurrencyPairToExchange(CurrencyPair currencyPair) {
    return new CurrencyPair(adaptCurrencyToExchange(currencyPair.base), adaptCurrencyToExchange(currencyPair.counter));
  }

  public static Currency adaptCurrencyToExchange(Currency currency) {
    if (currency == Currency.BTC) {
      return currency.getIso4217Currency();
    }
    return currency;
  }
}
