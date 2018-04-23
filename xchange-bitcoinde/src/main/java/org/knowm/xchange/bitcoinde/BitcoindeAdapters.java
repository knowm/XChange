package org.knowm.xchange.bitcoinde;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bitcoinde.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.dto.account.BitcoindeBalance;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrder;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** @author matthewdowney & frank kaiser */
public final class BitcoindeAdapters {

  public static final Comparator<LimitOrder> ASK_COMPARATOR =
      (o1, o2) -> o1.getLimitPrice().compareTo(o2.getLimitPrice());
  public static final Comparator<LimitOrder> BID_COMPARATOR =
      (o1, o2) -> o2.getLimitPrice().compareTo(o1.getLimitPrice());

  /** Private constructor. */
  private BitcoindeAdapters() {}

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook object to an OrderBook
   * object.
   *
   * @param bitcoindeOrderbookWrapper the exchange specific OrderBook object
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      BitcoindeOrderbookWrapper bitcoindeOrderbookWrapper, CurrencyPair currencyPair) {

    // System.out.println("bitcoindeOrderbookWrapper = " +
    // bitcoindeOrderbookWrapper);
    // System.out.println("credits = " + bitcoindeOrderbookWrapper.getCredits());

    List<LimitOrder> asks =
        createOrders(
            currencyPair, OrderType.ASK, bitcoindeOrderbookWrapper.getBitcoindeOrders().getAsks());
    List<LimitOrder> bids =
        createOrders(
            currencyPair, OrderType.BID, bitcoindeOrderbookWrapper.getBitcoindeOrders().getBids());

    bids.sort(BID_COMPARATOR);
    asks.sort(ASK_COMPARATOR);
    return new OrderBook(null, asks, bids);
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeAccount object to an AccountInfo
   * object.
   *
   * @param bitcoindeAccount
   * @return
   */
  public static AccountInfo adaptAccountInfo(BitcoindeAccountWrapper bitcoindeAccount) {

    // This adapter is not complete yet
    BitcoindeBalance btc = bitcoindeAccount.getData().getBalances().getBtc();
    BitcoindeBalance eth = bitcoindeAccount.getData().getBalances().getEth();
    BigDecimal eur = bitcoindeAccount.getData().getFidorReservation().getAvailableAmount();

    org.knowm.xchange.dto.account.Balance btcBalance =
        new Builder().setCurrency(Currency.BTC).setTotal(btc.getAvailableAmount()).createBalance();
    org.knowm.xchange.dto.account.Balance ethBalance =
        new Builder().setCurrency(Currency.ETH).setTotal(eth.getAvailableAmount()).createBalance();
    org.knowm.xchange.dto.account.Balance eurBalance =
        new Builder().setCurrency(Currency.EUR).setTotal(eur).createBalance();

    Wallet wallet = Wallet.build(btcBalance, ethBalance, eurBalance);

    return AccountInfo.build(wallet);
  }

  /** Create a list of orders from a list of asks or bids. */
  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, BitcoindeOrder[] orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BitcoindeOrder order : orders) {
      limitOrders.add(createOrder(currencyPair, order, orderType, null, null));
    }
    return limitOrders;
  }

  /** Create an individual order. */
  public static LimitOrder createOrder(
      CurrencyPair currencyPair,
      BitcoindeOrder bitcoindeOrder,
      OrderType orderType,
      String orderId,
      Date timeStamp) {

    return new LimitOrder(
        orderType,
        bitcoindeOrder.getAmount(),
        currencyPair,
        orderId,
        timeStamp,
        bitcoindeOrder.getPrice());
  }

  /**
   * Adapt a org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade[] object to a Trades object.
   *
   * @param bitcoindeTradesWrapper Exchange specific trades
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(
      BitcoindeTradesWrapper bitcoindeTradesWrapper, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitcoindeTrade bitcoindeTrade : bitcoindeTradesWrapper.getTrades()) {
      final long tid = bitcoindeTrade.getTid();
      if (tid > lastTradeId) {
        lastTradeId = tid;
      }
      trades.add(
          new Trade(
              null,
              bitcoindeTrade.getAmount(),
              currencyPair,
              bitcoindeTrade.getPrice(),
              DateUtils.fromMillisUtc(bitcoindeTrade.getDate() * 1000L),
              String.valueOf(tid)));
    }
    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * @param bitcoindeOpenOrdersWrapper
   * @return
   */
  public static OpenOrders adaptOpenOrders(
      BitcoindeMyOpenOrdersWrapper bitcoindeOpenOrdersWrapper) {
    System.out.println(bitcoindeOpenOrdersWrapper);

    List<BitcoindeMyOrder> bitcoindeMyOrders = bitcoindeOpenOrdersWrapper.getOrders();

    List<LimitOrder> orders = new ArrayList<>(bitcoindeMyOrders.size());

    for (BitcoindeMyOrder bitcoindeMyOrder : bitcoindeMyOrders) {
      CurrencyPair tradingPair =
          CurrencyPairDeserializer.getCurrencyPairFromString(bitcoindeMyOrder.getTradingPair());

      Date timestamp = fromRfc3339DateStringQuietly(bitcoindeMyOrder.getCreatedAt());

      OrderType otype = "buy".equals(bitcoindeMyOrder.getType()) ? OrderType.BID : OrderType.ASK;
      LimitOrder limitOrder =
          new LimitOrder(
              otype,
              bitcoindeMyOrder.getMaxAmount(),
              tradingPair,
              bitcoindeMyOrder.getOrderId(),
              timestamp,
              bitcoindeMyOrder.getPrice());
      orders.add(limitOrder);
    }

    return new OpenOrders(orders);
  }

  private static Date fromRfc3339DateStringQuietly(String timestamp) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
      return simpleDateFormat.parse(timestamp);
    } catch (ParseException e) {
      return null;
    }
  }
}
