package com.xeiam.xchange.mercadobitcoin;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTicker;
import com.xeiam.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrdersEntry;
import com.xeiam.xchange.mercadobitcoin.dto.trade.OperationEntry;
import com.xeiam.xchange.utils.DateUtils;

import static com.xeiam.xchange.utils.DateUtils.fromUnixTime;

/**
 * Various adapters for converting from Mercado Bitcoin DTOs to XChange DTOs
 *
 * @author Felipe Micaroni Lalli
 */
public final class MercadoBitcoinAdapters {

  /**
   * private Constructor
   */
  private MercadoBitcoinAdapters() {

  }

  /**
   * Adapts a com.xeiam.xchange.mercadobitcoin.dto.marketdata.OrderBook to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/BRL or LTC/BRL)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(MercadoBitcoinOrderBook mercadoBitcoinOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, mercadoBitcoinOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, mercadoBitcoinOrderBook.getBids());
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a MercadoBitcoinTicker to a Ticker Object
   *
   * @param mercadoBitcoinTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(MercadoBitcoinTicker mercadoBitcoinTicker, CurrencyPair currencyPair) {

    BigDecimal last = mercadoBitcoinTicker.getTicker().getLast();
    BigDecimal bid = mercadoBitcoinTicker.getTicker().getBuy();
    BigDecimal ask = mercadoBitcoinTicker.getTicker().getSell();
    BigDecimal high = mercadoBitcoinTicker.getTicker().getHigh();
    BigDecimal low = mercadoBitcoinTicker.getTicker().getLow();
    BigDecimal volume = mercadoBitcoinTicker.getTicker().getVol();
    Date timestamp = new Date(mercadoBitcoinTicker.getTicker().getDate() * 1000L);

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();

  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Mercado Bitcoin transactions
   * @param currencyPair (e.g. BTC/BRL or LTC/BRL)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(MercadoBitcoinTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (MercadoBitcoinTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(new Trade(toOrderType(tx.getType()), tx.getAmount(), currencyPair, tx.getPrice(),
          DateUtils.fromMillisUtc(tx.getDate() * 1000L), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  /**
   * Adapts a MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> to an AccountInfo
   *
   * @param accountInfo The Mercado Bitcoin accountInfo
   * @param userName The user name
   * @return The account info
   */
  public static AccountInfo adaptAccountInfo(MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> accountInfo, String userName) {

    // Adapt to XChange DTOs
    Balance brlBalance = new Balance(Currency.BRL, accountInfo.getTheReturn().getFunds().getBrl());
    Balance btcBalance = new Balance(Currency.BTC, accountInfo.getTheReturn().getFunds().getBtc());
    Balance ltcBalance = new Balance(Currency.LTC, accountInfo.getTheReturn().getFunds().getLtc());

    return new AccountInfo(userName, new Wallet(brlBalance, btcBalance, ltcBalance));
  }

  public static List<LimitOrder> adaptOrders(CurrencyPair currencyPair, MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> input) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    MercadoBitcoinUserOrders orders = input.getTheReturn();

    for (Map.Entry<String, MercadoBitcoinUserOrdersEntry> entry : orders.entrySet()) {
      limitOrders.add(processOrderEntry(entry, currencyPair));
    }

    return limitOrders;
  }

  private static LimitOrder processOrderEntry(Map.Entry<String, MercadoBitcoinUserOrdersEntry> entry, CurrencyPair currencyPair) {

    String id = entry.getKey();
    MercadoBitcoinUserOrdersEntry userOrdersEntry = entry.getValue();

    String type = userOrdersEntry.getType();
    OrderType orderType = toOrderType(type);
    BigDecimal price = userOrdersEntry.getPrice();
    BigDecimal volume = userOrdersEntry.getVolume();
    long time = userOrdersEntry.getCreated() * 1000L;
    return new LimitOrder(orderType, volume, currencyPair, id, new Date(time), price);
  }

  private static OrderType toOrderType(String mercadoType) {
    return mercadoType.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {
    return (pair.base.getCurrencyCode() + "_" + pair.counter.getCurrencyCode()).toLowerCase();
  }

  public static UserTrades toUserTrades(CurrencyPair pair, MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> orders) {
    List<UserTrade> result = new LinkedList<>();
    for (Map.Entry<String, MercadoBitcoinUserOrdersEntry> e : orders.getTheReturn().entrySet()) {
      String orderId = e.getKey();
      MercadoBitcoinUserOrdersEntry order = e.getValue();
      OrderType type = toOrderType(order.getType());
      for (Map.Entry<String, OperationEntry> f : order.getOperations().entrySet()) {
        String txId = f.getKey();
        OperationEntry op = f.getValue();
        result
            .add(new UserTrade.Builder().currencyPair(pair).id(txId).orderId(orderId).price(op.getPrice()).timestamp(fromUnixTime(op.getCreated())).tradableAmount(op.getVolume()).type(type).build());
      }
    }
    // TODO verify sortType
    return new UserTrades(result, Trades.TradeSortType.SortByID);
  }
}
