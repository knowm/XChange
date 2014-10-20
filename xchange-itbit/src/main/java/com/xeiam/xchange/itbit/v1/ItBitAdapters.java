package com.xeiam.xchange.itbit.v1;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountBalance;
import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTrade;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitOrder;
import com.xeiam.xchange.utils.DateUtils;

public final class ItBitAdapters {

  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder> emptyList());
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  /**
   * private Constructor
   */
  private ItBitAdapters() {

  }

  private static Date parseDate(String date) {

    Date parse;
    try {
      /**
       * "date" is sent with microsecond precision in UTC time. This is not supported by Java natively.
       */
      parse = dateFormat.parse(date.substring(0, 23) + 'Z');
    } catch (ParseException e) {
      return null;
    }

    return parse;
  }

  public static Trades adaptTrades(ItBitTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(trades.length);
    long lastTradeId = 0;
    for (int i = 0; i < trades.length; i++) {
      ItBitTrade trade = trades[i];
      long tradeId = trade.getTid();
      if (tradeId > lastTradeId)
        lastTradeId = tradeId;
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public static Trade adaptTrade(ItBitTrade trade, CurrencyPair currencyPair) {

    Date date = DateUtils.fromMillisUtc(trade.getDate() * 1000L);
    final String tradeId = String.valueOf(trade.getTid());

    return new Trade(null, trade.getAmount(), currencyPair, trade.getPrice(), date, tradeId);
  }

  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> orders, CurrencyPair currencyPair, OrderType orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());

    for (int i = 0; i < orders.size(); i++) {
      BigDecimal[] level = orders.get(i);

      limitOrders.add(adaptOrder(level[1], level[0], currencyPair, null, orderType));
    }

    return limitOrders;

  }

  private static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderId, OrderType orderType) {

    return new LimitOrder(orderType, amount, currencyPair, orderId, null, price);
  }

  public static AccountInfo adaptAccountInfo(ItBitAccountInfoReturn[] info) {

    List<Wallet> wallets = new ArrayList<Wallet>();
    String userId = "";

    for (int i = 0; i < info.length; i++) {
      ItBitAccountInfoReturn itBitAccountInfoReturn = info[i];
      ItBitAccountBalance[] balances = itBitAccountInfoReturn.getBalances();

      userId = itBitAccountInfoReturn.getUserId();

      for (int j = 0; j < balances.length; j++) {
        ItBitAccountBalance itBitAccountBalance = balances[j];

        Wallet wallet = new Wallet(itBitAccountBalance.getCurrency(), itBitAccountBalance.getAvailableBalance(), itBitAccountInfoReturn.getName());
        wallets.add(wallet);
      }
    }

    return new AccountInfo(userId, wallets);
  }

  public static OpenOrders adaptPrivateOrders(ItBitOrder[] orders) {

    if (orders.length <= 0) {
      return noOpenOrders;
    }

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);

    for (int i = 0; i < orders.length; i++) {
      ItBitOrder itBitOrder = orders[i];
      String instrument = itBitOrder.getInstrument();

      CurrencyPair currencyPair = new CurrencyPair(instrument.substring(0, 3), instrument.substring(3, 6));
      OrderType orderType = itBitOrder.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;

      limitOrders.add(adaptOrder(itBitOrder.getAmount(), itBitOrder.getPrice(), currencyPair, itBitOrder.getId(), orderType));
    }

    return new OpenOrders(limitOrders);
  }

  public static Trades adaptTradeHistory(ItBitOrder[] orders) {

    List<Trade> trades = new ArrayList<Trade>(orders.length);

    for (int i = 0; i < orders.length; i++) {
      ItBitOrder itBitOrder = orders[i];
      String instrument = itBitOrder.getInstrument();

      OrderType orderType = itBitOrder.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = new CurrencyPair(instrument.substring(0, 3), instrument.substring(3, 6));
      Date timestamp = parseDate(itBitOrder.getCreatedTime());

      trades.add(new Trade(orderType, itBitOrder.getAmount(), currencyPair, itBitOrder.getPrice(), timestamp, itBitOrder.getId()));
    }

    return new Trades(trades, TradeSortType.SortByTimestamp);
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, ItBitTicker itBitTicker) {

    BigDecimal bid = itBitTicker.getBid();
    BigDecimal ask = itBitTicker.getAsk();
    BigDecimal high = itBitTicker.getHighToday();
    BigDecimal low = itBitTicker.getLowToday();
    BigDecimal last = itBitTicker.getLastPrice();
    BigDecimal volume = itBitTicker.getVolume24h();
    Date timestamp = parseDate(itBitTicker.getTimestamp());

    return Ticker.TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }
}
