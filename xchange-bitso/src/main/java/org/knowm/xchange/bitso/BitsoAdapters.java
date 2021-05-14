package org.knowm.xchange.bitso;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.account.BitsoBalances;
import org.knowm.xchange.bitso.dto.marketdata.BitsoCommonOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTickerPayload;
import org.knowm.xchange.bitso.dto.trade.BitsoTrade;
import org.knowm.xchange.bitso.dto.trade.BitsoTrades;
import org.knowm.xchange.bitso.dto.trade.BitsoUserTransaction;
import org.knowm.xchange.bitso.dto.trade.Payload;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.utils.DateUtils;

public final class BitsoAdapters {

  private BitsoAdapters() {}

  public static Ticker adaptTicker(BitsoTicker t, CurrencyPair currencyPair) {

    BitsoTickerPayload t1 = t.getPayload();
    System.out.println("Bitso Adapter class and adaptTicker method and BitsoPayload Value is");
    System.out.println(t1);
    return new Ticker.Builder()
        .instrument(currencyPair)
        .last(t1.getLast())
        .bid(t1.getBid())
        .ask(t1.getAsk())
        .high(t1.getHigh())
        .low(t1.getLow())
        .vwap(new BigDecimal(t1.getVwap()))
        .volume(new BigDecimal(t1.getVolume()))
        .timestamp(t1.getTimestamp())
        .build();
  }

  public static Wallet adaptWallet(BitsoBalance bitsoBalance) {
    // Adapt to XChange DTOs
    List<Balance> balancesList = new ArrayList<>();
    Balance balances = null;
    for (BitsoBalances balance : bitsoBalance.getPayload().getBalances()) {

      balances =
          new Balance(
              Currency.getInstance(balance.getCurrency()),
              new BigDecimal(balance.getTotal()),
              new BigDecimal(balance.getAvailable()),
              new BigDecimal(balance.getLocked()));
      balancesList.add(balances);
    }

    return Wallet.Builder.from(balancesList).build();
  }

  public static OrderBook adaptOrderBook(
      BitsoOrderBook bitsoOrderBook, CurrencyPair currencyPair, int timeScale) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'SS:SS");

    List<LimitOrder> asks =
        createOrders(currencyPair, Order.OrderType.ASK, bitsoOrderBook.getPayload().getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, Order.OrderType.BID, bitsoOrderBook.getPayload().getBids());
    Date bitsoTimestamp = null;
    try {
      bitsoTimestamp = format.parse(bitsoOrderBook.getPayload().getTimestamp());
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    Date date =
        new Date(
            bitsoTimestamp.getTime()
                * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, List<BitsoCommonOrderBook> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BitsoCommonOrderBook ask : orders) {
      //      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0}
      // elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, BitsoCommonOrderBook priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(
        orderType,
        new BigDecimal(priceAndAmount.getAmount()),
        currencyPair,
        "",
        null,
        new BigDecimal(priceAndAmount.getPrice()));
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Bitso transactions
   * @param currencyPair (e.g. BTC/MXN)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitsoTrades transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitsoTrade tx : transactions.getPayload()) {
      Order.OrderType type;
      switch (tx.getMakerSide()) {
        case "buy":
          type = Order.OrderType.ASK;
          break;
        case "sell":
          type = Order.OrderType.BID;
          break;
        default:
          type = null;
      }
      final long tradeId = tx.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(
          new Trade.Builder()
              .type(type)
              .originalAmount(tx.getAmount())
              .currencyPair(currencyPair)
              .price(tx.getPrice())
              .timestamp(DateUtils.fromMillisUtc(tx.getCreatedAt().getTime() * 1000L))
              .id(String.valueOf(tradeId))
              .build());
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static UserTrades adaptTradeHistory(BitsoUserTransaction[] bitsoUserTransactions) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (BitsoUserTransaction bitsoUserTransaction : bitsoUserTransactions) {
      if (bitsoUserTransaction
          .getType()
          .equals(
              BitsoUserTransaction.TransactionType
                  .trade)) { // skip account deposits and withdrawals.
        boolean sell = bitsoUserTransaction.getMxn().doubleValue() > 0.0;
        Order.OrderType orderType = sell ? Order.OrderType.ASK : Order.OrderType.BID;
        BigDecimal originalAmount = bitsoUserTransaction.getBtc();
        BigDecimal price = bitsoUserTransaction.getPrice().abs();
        Date timestamp = BitsoUtils.parseDate(bitsoUserTransaction.getDatetime());
        long transactionId = bitsoUserTransaction.getId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId = String.valueOf(bitsoUserTransaction.getOrderId());
        final BigDecimal feeAmount = bitsoUserTransaction.getFee();
        final CurrencyPair currencyPair = new CurrencyPair(Currency.BTC, Currency.MXN);

        String feeCurrency =
            sell ? currencyPair.counter.getCurrencyCode() : currencyPair.base.getCurrencyCode();
        UserTrade trade =
            new UserTrade.Builder()
                .type(orderType)
                .originalAmount(originalAmount)
                .currencyPair(currencyPair)
                .price(price)
                .timestamp(timestamp)
                .id(tradeId)
                .orderId(orderId)
                .feeAmount(feeAmount)
                .feeCurrency(Currency.getInstance(feeCurrency))
                .build();
        trades.add(trade);
      }
    }

    return new UserTrades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  public static Order adaptOrder(Payload order) {
    OrderType type = order.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
    CurrencyPair currencyPair = new CurrencyPair(order.getBook().replace('_', '/'));
    Order.Builder builder = null;
    if (order.getType().equals("market")) {
      builder = new MarketOrder.Builder(type, currencyPair);
    } else if (order.getType().equals("limit")) {
      if (order.getUnfilledAmount() == null) {
        builder =
            new LimitOrder.Builder(type, currencyPair).limitPrice(new BigDecimal(order.getPrice()));
      } else {
        builder =
            new StopOrder.Builder(type, currencyPair)
                .stopPrice(new BigDecimal(order.getUnfilledAmount()));
      }
    }
    if (builder == null) {
      return null;
    }
    builder
        .orderStatus(adaptOrderStatus(order))
        .originalAmount(new BigDecimal(order.getOriginalAmount()))
        .id(order.getOid())
        .timestamp(adaptDate(order.getUpdatedAt()));
    //	        .cumulativeAmount(order.getFilledSize());

    //	    BigDecimal averagePrice;
    //	    if (order.getFilledSize().signum() != 0 && order.getExecutedvalue().signum() != 0) {
    //	      averagePrice = order.getExecutedvalue().divide(order.getFilledSize(),
    // MathContext.DECIMAL32);
    //	    } else {
    //	      averagePrice = BigDecimal.ZERO;
    //	    }
    //	    builder.averagePrice(averagePrice);
    return builder.build();
  }
  /** The status from the CoinbaseProOrder object converted to xchange status */
  public static OrderStatus adaptOrderStatus(Payload order) {

    if (order.getStatus().equals("pending")) {
      return OrderStatus.PENDING_NEW;
    }

    if (order.getStatus().equals("completed") || order.getStatus().equals("settled")) {

      if (order.getStatus().equals("filled")) {
        return OrderStatus.FILLED;
      }

      if (order.getStatus().equals("canceled")) {
        return OrderStatus.CANCELED;
      }

      return OrderStatus.UNKNOWN;
    }

    //    if (order.getUnfilledAmount().signum() == 0) {
    //
    //      if (order.getStatus().equals("open") && order.getStop() != null) {
    //        // This is a massive edge case of a stop triggering but not immediately
    //        // fulfilling.  STOPPED status is only currently used by the HitBTC and
    //        // YoBit implementations and in both cases it looks like a
    //        // misunderstanding and those should return CANCELLED.  Should we just
    //        // remove this status?
    //        return OrderStatus.STOPPED;
    //      }
    //
    //      return OrderStatus.NEW;
    //    }
    //
    //    if (order.getFilledSize().compareTo(BigDecimal.ZERO) > 0
    //        // if size >= filledSize order should be partially filled
    //        && order.getSize().compareTo(order.getFilledSize()) >= 0)
    //      return OrderStatus.PARTIALLY_FILLED;

    return OrderStatus.UNKNOWN;
  }

  public static Date adaptDate(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'SSSS");
    try {
      return format.parse(date);
    } catch (ParseException e) {
      System.err.println("Date is not Parse from Bitso Adapter Adapt Date Function...");
    }
    return null;
  }

  public static OrderType convertType(boolean isBuyer) {
    return isBuyer ? OrderType.BID : OrderType.ASK;
  }

  public static CurrencyPair adaptSymbol(String symbol) {
    int pairLength = symbol.length();
    if (symbol.endsWith("USDT")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "USDT");
    } else if (symbol.endsWith("USDC")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "USDC");
    } else if (symbol.endsWith("TUSD")) {
      return new CurrencyPair(symbol.substring(0, pairLength - 4), "TUSD");
    } else {
      return symbol.endsWith("USDS") ? new CurrencyPair(symbol.substring(0, pairLength - 4), "USDS") : new CurrencyPair(symbol.substring(0, pairLength - 3), symbol.substring(pairLength - 3));
    }
  }
}
