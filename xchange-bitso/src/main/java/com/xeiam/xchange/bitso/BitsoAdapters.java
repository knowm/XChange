package com.xeiam.xchange.bitso;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.bitso.dto.account.BitsoBalance;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoOrderBook;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTicker;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTransaction;
import com.xeiam.xchange.bitso.dto.trade.BitsoUserTransaction;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.utils.DateUtils;

public final class BitsoAdapters {

  private BitsoAdapters() { }

  public static Ticker adaptTicker(BitsoTicker t, CurrencyPair currencyPair) {

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(t.getLast())
        .bid(t.getBid())
        .ask(t.getAsk())
        .high(t.getHigh())
        .low(t.getLow())
        .vwap(t.getVwap())
        .volume(t.getVolume())
        .timestamp(t.getTimestamp())
        .build();
  }

  public static AccountInfo adaptAccountInfo(BitsoBalance bitsoBalance, String userName) {
    // Adapt to XChange DTOs
    Wallet mxnWallet = new Wallet(Currencies.MXN, bitsoBalance.getMxnBalance(), bitsoBalance.getMxnAvailable(), bitsoBalance.getMxnReserved());
    Wallet btcWallet = new Wallet(Currencies.BTC, bitsoBalance.getBtcBalance(), bitsoBalance.getBtcAvailable(), bitsoBalance.getBtcReserved());

    return new AccountInfo(userName, Arrays.asList(mxnWallet, btcWallet));
  }

  public static OrderBook adaptOrderBook(BitsoOrderBook bitstampOrderBook, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitstampOrderBook.getBids());
    Date date = new Date(bitstampOrderBook.getTimestamp() * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }
  
  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Bitso transactions
   * @param currencyPair (e.g. BTC/MXN)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitsoTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<Trade>();
    long lastTradeId = 0;
    for (BitsoTransaction tx : transactions) {
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades
          .add(new Trade(null, tx.getAmount(), currencyPair, tx.getPrice(), DateUtils.fromMillisUtc(tx.getDate() * 1000L), String.valueOf(tradeId)));
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }


  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static UserTrades adaptTradeHistory(BitsoUserTransaction[] bitsoUserTransactions) {

    List<UserTrade> trades = new ArrayList<UserTrade>();
    long lastTradeId = 0;
    for (BitsoUserTransaction bitstampUserTransaction : bitsoUserTransactions) {
      if (bitstampUserTransaction.getType().equals(BitsoUserTransaction.TransactionType.trade)) { // skip account deposits and withdrawals.
        boolean sell = bitstampUserTransaction.getMxn().doubleValue() > 0.0;
        Order.OrderType orderType = sell ? Order.OrderType.ASK : Order.OrderType.BID;
        BigDecimal tradableAmount = bitstampUserTransaction.getBtc();
        BigDecimal price = bitstampUserTransaction.getPrice().abs();
        Date timestamp = BitsoUtils.parseDate(bitstampUserTransaction.getDatetime());
        long transactionId = bitstampUserTransaction.getId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId = String.valueOf(bitstampUserTransaction.getOrderId());
        final BigDecimal feeAmount = bitstampUserTransaction.getFee();
        final CurrencyPair currencyPair = new CurrencyPair(Currencies.BTC, Currencies.MXN);

        String feeCurrency = sell ? currencyPair.counterSymbol : currencyPair.baseSymbol;
        UserTrade trade = new UserTrade(orderType, tradableAmount, currencyPair, price, timestamp, tradeId, orderId, feeAmount, feeCurrency);
        trades.add(trade);
      }
    }

    return new UserTrades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }
}
