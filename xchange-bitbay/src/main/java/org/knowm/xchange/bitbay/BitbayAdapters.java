package org.knowm.xchange.bitbay;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.bitbay.dto.acount.BitbayAccountInfoResponse;
import org.knowm.xchange.bitbay.dto.acount.BitbayBalance;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTicker;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTrade;
import org.knowm.xchange.bitbay.dto.trade.BitbayOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

/**
 * @author kpysniak
 */
public class BitbayAdapters {

  /**
   * Singleton
   */
  private BitbayAdapters() {

  }

  /**
   * Adapts a BitbayTicker to a Ticker Object
   *
   * @param bitbayTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(BitbayTicker bitbayTicker, CurrencyPair currencyPair) {

    BigDecimal ask = bitbayTicker.getAsk();
    BigDecimal bid = bitbayTicker.getBid();
    BigDecimal high = bitbayTicker.getMax();
    BigDecimal low = bitbayTicker.getMin();
    BigDecimal volume = bitbayTicker.getVolume();
    BigDecimal last = bitbayTicker.getLast();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).build();
  }

  /**
   * @param orders
   * @param orderType
   * @param currencyPair
   * @return
   */
  private static List<LimitOrder> transformArrayToLimitOrders(BigDecimal[][] orders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (BigDecimal[] order : orders) {
      limitOrders.add(new LimitOrder(orderType, order[1], currencyPair, null, new Date(), order[0]));
    }

    return limitOrders;
  }

  /**
   * @param bitbayOrderBook
   * @param currencyPair
   * @return
   */
  public static OrderBook adaptOrderBook(BitbayOrderBook bitbayOrderBook, CurrencyPair currencyPair) {

    OrderBook orderBook = new OrderBook(null, transformArrayToLimitOrders(bitbayOrderBook.getAsks(), OrderType.ASK, currencyPair),
        transformArrayToLimitOrders(bitbayOrderBook.getBids(), OrderType.BID, currencyPair));

    return orderBook;
  }

  /**
   * @param bitbayTrades
   * @param currencyPair
   * @return
   */
  public static Trades adaptTrades(BitbayTrade[] bitbayTrades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>();

    for (BitbayTrade bitbayTrade : bitbayTrades) {

      Trade trade = new Trade(null, bitbayTrade.getAmount(), currencyPair, bitbayTrade.getPrice(), new Date(bitbayTrade.getDate() * 1000),
          bitbayTrade.getTid());

      tradeList.add(trade);
    }

    Trades trades = new Trades(tradeList, Trades.TradeSortType.SortByTimestamp);
    return trades;
  }

  public static AccountInfo adaptAccountInfo(String userName, BitbayAccountInfoResponse bitbayAccountInfo) {
    List<Balance> balances = new ArrayList<>(bitbayAccountInfo.getBitbayBalances().size());

    for (Map.Entry<String, BitbayBalance> entry : bitbayAccountInfo.getBitbayBalances().entrySet()) {
      Currency currency = Currency.getInstance(entry.getKey());
      BitbayBalance balance = entry.getValue();

      balances.add(new Balance(currency, balance.getAvailable().add(balance.getLocked()), balance.getAvailable(), balance.getLocked()));
    }

    return new AccountInfo(userName, new Wallet(balances));
  }

  public static OpenOrders adaptOpenOrders(List<BitbayOrder> orders) {
    List<LimitOrder> result = new ArrayList<>();

    for (BitbayOrder order : orders) {
      if ("active".equals(order.getStatus())) {
        result.add(createOrder(order));
      }
    }

    return new OpenOrders(result);
  }
 public static UserTrades adaptTradeHistory(List<BitbayOrder> orders) {
    List<UserTrade> result = new ArrayList<>();

    for (BitbayOrder order : orders) {
      if ("inactive".equals(order.getStatus())) {
        result.add(createUserTrade(order));
      }
    }

    return new UserTrades(result, Trades.TradeSortType.SortByTimestamp);
  }

  private static LimitOrder createOrder(BitbayOrder bitbayOrder) {
    CurrencyPair currencyPair = new CurrencyPair(bitbayOrder.getCurrency(), bitbayOrder.getPaymentCurrency());
    OrderType type = "ask".equals(bitbayOrder.getType()) ? OrderType.ASK : OrderType.BID;

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm:SS");
    Date date;
    try {
      date = formatter.parse(bitbayOrder.getDate());
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }

    return new LimitOrder(type, bitbayOrder.getAmount(), currencyPair, String.valueOf(bitbayOrder.getId()), date,
        bitbayOrder.getStartPrice().divide(bitbayOrder.getStartAmount()));
  }
  private static UserTrade createUserTrade(BitbayOrder bitbayOrder) {
    CurrencyPair currencyPair = new CurrencyPair(bitbayOrder.getCurrency(), bitbayOrder.getPaymentCurrency());
    OrderType type = "ask".equals(bitbayOrder.getType()) ? OrderType.ASK : OrderType.BID;

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD HH:mm:SS");
    Date date;
    try {
      date = formatter.parse(bitbayOrder.getDate());
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }

    return new UserTrade(type,
            bitbayOrder.getAmount(),
            currencyPair,
            bitbayOrder.getCurrentPrice().divide(bitbayOrder.getStartAmount()),
            date,
            String.valueOf(bitbayOrder.getId()),  String.valueOf(bitbayOrder.getId()), null,null);
  }
}
